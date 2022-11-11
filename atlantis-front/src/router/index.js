import Vue from 'vue'
import VueRouter from 'vue-router'
import storage from '@/utils/storage'

import Page from '../components/HomePage/Page.vue'
import Introduction from '../components/Pages/Introduction.vue'
import News from '../components/Pages/News.vue'
import Tutorial from '../components/Pages/Tutorial.vue'
import Forum from '../components/Pages/Forum.vue'
import About from '../components/Pages/About.vue'
import AboutIntroduction from '../components/Pages/SubPages/AboutIntroduction.vue'
import AboutMember from '../components/Pages/SubPages/AboutMember.vue'
import AboutContact from '../components/Pages/SubPages/AboutContact.vue'
import TutorialContent from '../components/Pages/SubPages/TutorialContent.vue'
import NewsContent from '../components/Pages/SubPages/NewsContent.vue'
import ForumContent from '../components/Pages/SubPages/ForumContent.vue'
import Login from '../components/Login/Login.vue'
import Signup from '../components/Signup/Signup.vue'
import Dashboard from '../components/Dashboard/Dashboard.vue'
import DashHome from '../components/Dashboard/DashHome.vue'
import DashAccount from '../components/Dashboard/DashAccount.vue'
import DashMessage from '../components/Dashboard/DashMessage.vue'
import DashNews from '../components/Dashboard/DashNews.vue'
import DashTutorial from '../components/Dashboard/DashTutorial.vue'
import DashForum from '../components/Dashboard/DashForum.vue'
import DashAbout from '../components/Dashboard/DashAbout.vue'
import Personal from '../components/PersonalPage/Personal.vue'
import PersonalProfile from '../components/PersonalPage/PersonalProfile.vue'
import PersonalMessage from '../components/PersonalPage/PersonalMessage.vue'


Vue.use(VueRouter)

const routes =
    [
        // log in and sign up router
        {
            path: "/login/:type",
            component: Login,
            meta: {
                keepAlive: true // 需要缓存
            },
            props: true
        },
        {
            path: "/signup",
            component: Signup,
            meta: {
                keepAlive: true // 需要缓存
            },
            props: true
        },
        // home page router
        {
            path: "/",
            redirect: "/page",
            // meta: {
            //     keepAlive: true // 需要缓存
            // },
        },
        {
            path: "/page",
            redirect: "/page/introduction",
            component: Page,
            children: [
                {
                    path: "introduction",
                    component: Introduction
                },
                {
                    path: "news",
                    // 重定向至默认界面，设为“游戏新闻”
                    redirect: "/page/news/news",
                    component: News,
                    props: true,
                    // 动态路由
                    children: [
                        {
                            path: ":type",
                            component: NewsContent,
                            props: true
                        }
                    ]
                },
                {
                    path: "tutorial",
                    redirect: "/page/tutorial/basic",
                    component: Tutorial,
                    children: [
                        {
                            path: ":type",
                            component: TutorialContent,
                            props: true
                        }
                    ]
                },
                {
                    path: "forum",
                    component: Forum
                },
                {
                    path: "about",
                    redirect: "/page/about/introduction",
                    component: About,
                    children: [
                        {
                            path: "introduction",
                            component: AboutIntroduction,
                            props: true
                        },
                        {
                            path: "member",
                            component: AboutMember,
                            props: true
                        },
                        {
                            path: "contact",
                            component: AboutContact,
                            props: true
                        }
                    ]
                }
            ]
        },
        // dashboard router
        {
            path: "/dashboard",
            redirect: "/dashboard/home",
            component: Dashboard,
            // meta: {
            //     keepAlive: true // 需要缓存
            // },
            props: true,
            children: [
                {
                    path: "home",
                    component: DashHome,
                    props: true,
                },
                {
                    path: "account/:type",
                    component: DashAccount,
                    props: true,
                },
                {
                    path: "message/:type",
                    component: DashMessage,
                    props: true,
                },
                {
                    path: "news",
                    component: DashNews,
                    props: true,
                },
                {
                    path: "tutorial",
                    component: DashTutorial,
                    props: true,
                },
                {
                    path: "forum",
                    component: DashForum,
                    props: true,
                },
                {
                    path: "about",
                    component: DashAbout,
                    props: true,
                },
            ],
            // 添加单独路由守卫
            beforeEnter: (to, from, next) => {
                const accountInfo = storage.get('accountInfo');
                const loginType = storage.get('loginType');

                // 正常是不会出现这种情况
                if (accountInfo === null)
                {
                    return next("/login/admin")
                }
                else 
                {
                    if (loginType.includes("admin"))
                    {
                        return next();
                    }
                    else
                    {
                        return next(from);
                    }
                }
            },
        },
        // personal page router
        {
            path: "/personal",
            redirect: "/personal/profile",
            component: Personal,
            // meta: {
            //     keepAlive: true // 需要缓存
            // },
            // props: true,
            children: [
                {
                    path: "/personal/profile",
                    component: PersonalProfile,
                    props: true
                },
                {
                    path: "/personal/message",
                    component: PersonalMessage,
                    props: true
                },
            ],
            // 添加单独路由守卫
            beforeEnter: (to, from, next) => {
                const accountInfo = storage.get('accountInfo');
                const loginType = storage.get('loginType');

                // 正常是不会出现这种情况
                if (accountInfo === null)
                {
                    return next("/login/user")
                }
                else 
                {
                    if (loginType.includes("user"))
                    {
                        return next();
                    }
                    else
                    {
                        return next(from);
                    }
                }
            }
        }
    ]

const router = new VueRouter({
    // mode: 'history',
    // base: process.env.BASE_URL,
    routes
})

// 添加导航守卫
router.beforeEach((to, from, next) => {
    if (to.path.includes('login')) 
    {
        next()
    }
    // 其中包含了过期时间
    const accountInfo = storage.get("accountInfo");

    // 只要包含dashboard或personal，且localStorage内没有数据，强制返回
    if ((accountInfo === null) && (to.path.includes('dashboard') || to.path.includes('personal'))) 
    {
        if (to.path.includes('dashboard'))
        {
            return next('/login/admin');
        }
        else {
            return next('/login/user');
        }
    } 

    // localstorage里面存在数据，直接放行
    next()
})

export default router