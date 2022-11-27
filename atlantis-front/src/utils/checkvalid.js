export function checkIsValid(values){
    // 设置检验规则
    // 6-15个字符，仅包含英文大小写、数字及下划线（昵称除外）
    let reg = /^\w{6,15}$/;
    // value可能为字符串数组
    for (let i = 0; i < values.length; i++)
    {
        // 有一个检验失败
        if(!reg.test(values[i]) && values[i] !== null){
            console.log(values[i] + " is invalid")
            return false;
        }
    }
    // 都满足要求
    console.log("valid")
    return true;
}

export function checkIsValidForLowercase(value) {
    let reg = /[a-z]/;
    console.log(reg.test(value));
    return reg.test(value);
}