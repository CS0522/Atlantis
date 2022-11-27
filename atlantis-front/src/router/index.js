import Vue from 'vue'
import VueRouter from 'vue-router'
import storage from '@/utils/storage'

import Page from '../components/HomePage/Page.vue'
import Introduction from '../components/Pages/Introduction.vue'
import News from '../components/Pages/News.vue'
import Tutorial from '../components/Pages/Tutorial.vue'
import Forum from '../components/Pages/Forum.vue'
import About from '../components/Pages/About.vue'
import Dashboard from '@/components/Dashboard/Dashboard.vue'
import Personal from '@/components/PersonalPage/Personal.vue'

Vue.use(VueRouter)

const routes =
    [
        // log in and sign up router
        {
            path: "/login/:type",
            component: () => import('@/components/Login/Login.vue'),
            meta: {
                keepAlive: true // 需要缓存
            },
            props: true
        },
        {
            path: "/signup",
            component: () => import('@/components/Login/Login.vue'),
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
                    redirect: "/page/news/news/list",
                    component: News,
                    props: true,
                    // 动态路由
                    children: [
                        {
                            path: ":type",
                            component: () => import('@/components/Pages/SubPages/NewsContent.vue'),
                            props: true,
                            children: [
                                {
                                    path: "list",
                                    component: () => import('@/components/Pages/SubPages/NewsList.vue'),
                                    props: true,
                                },
                                {
                                    path: "detail",
                                    component: () => import('@/components/Pages/SubPages/NewsDetail.vue'),
                                    props: true,
                                }
                            ]
                        }
                    ]
                },
                {
                    path: "tutorial",
                    redirect: "/page/tutorial/basic/list",
                    component: Tutorial,
                    children: [
                        {
                            path: ":type",
                            component: () => import('@/components/Pages/SubPages/TutorialContent.vue'),
                            props: true,
                            children: [
                                {
                                    path: "list",
                                    component: () => import('@/components/Pages/SubPages/TutorialList.vue'),
                                    props: true,
                                },
                                {
                                    path: "detail",
                                    component: () => import('@/components/Pages/SubPages/TutorialDetail.vue'),
                                    props: true,
                                }
                            ]
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
                            component: () => import('@/components/Pages/SubPages/AboutIntroduction.vue'),
                            props: true
                        },
                        {
                            path: "member",
                            component: () => import('@/components/Pages/SubPages/AboutMember.vue'),
                            props: true
                        },
                        {
                            path: "contact",
                            component: () => import('@/components/Pages/SubPages/AboutContact.vue'),
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
                    component: () => import('@/components/Dashboard/DashHome.vue'),
                    props: true,
                },
                {
                    path: "account/:type",
                    component: () => import('@/components/Dashboard/DashAccount.vue'),
                    props: true,
                },
                {
                    path: "message/:type",
                    component: () => import('@/components/Dashboard/DashMessage.vue'),
                    props: true,
                },
                {
                    path: "news",
                    component: () => import('@/components/Dashboard/DashNews.vue'),
                    props: true,
                },
                {
                    path: "tutorial",
                    component: () => import('@/components/Dashboard/DashTutorial.vue'),
                    props: true,
                },
                {
                    path: "editor/:type/:id",
                    component: () => import('@/components/Dashboard/Editor.vue'), 
                    props: true, 
                },
                {
                    path: "forum",
                    component: () => import('@/components/Dashboard/DashForum.vue'),
                    props: true,
                },
                {
                    path: "about",
                    component: () => import('@/components/Dashboard/DashAbout.vue'),
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
                    component: () => import('@/components/PersonalPage/PersonalProfile.vue'),
                    props: true
                },
                {
                    path: "/personal/message",
                    component: () => import('@/components/PersonalPage/PersonalMessage.vue'),
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