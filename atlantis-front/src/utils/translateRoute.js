// 用于将路径翻译成中文，显示在 当前位置 处
// 传入长度为2的数组
// index === 1: introduction, news, tutorial, forum, about
// index === 2: null, {news, announces}, null, null, {introduction, member, contact}
export function translate(toTranslateList) {
    for (let i = 0; i < toTranslateList.length; i++)
    {
        if ('introduction' === toTranslateList[i])
        {
            toTranslateList[i] = '游戏介绍';

            toTranslateList[i + 1] = '';
        }
        else if ('news' === toTranslateList[i])
        {
            toTranslateList[i] = '游戏资讯';

            if ('news' === toTranslateList[i + 1])
            {
                toTranslateList[i + 1] = '游戏新闻';
            }
            else if ('announces' === toTranslateList[i + 1])
            {
                toTranslateList[i + 1] = '游戏公告';
            }
        }
        else if ('tutorial' === toTranslateList[i])
        {
            toTranslateList[i] = '游戏教程';

            toTranslateList[i + 1] = '';
        }
        else if ('forum' === toTranslateList[i])
        {
            toTranslateList[i] = '游戏论坛';

            toTranslateList[i + 1] = '';
        }
        else if ('about' === toTranslateList[i])
        {
            toTranslateList[i] = '关于车队';

            if ('introduction' === toTranslateList[i + 1])
            {
                toTranslateList[i + 1] = '车队简介';
            }
            else if ('member' === toTranslateList[i + 1])
            {
                toTranslateList[i + 1] = '车队成员';
            }
            else if ('contact' === toTranslateList[i + 1])
            {
                toTranslateList[i + 1] = '联系我们';
            }
        }
    }

    return toTranslateList;
}