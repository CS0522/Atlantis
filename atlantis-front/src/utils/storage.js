// localStorage 没有过期时间
// 因此封装 storage，将时间进行存储

let storage = {
    /*
    * set 存储方法
    * @ param {String}     key 键
    * @ param {String}     value 值，
    * @ param {String}     expired 过期时间，以毫秒为单位，非必须
    */
    set(key, val, expired) {
        let obj = {
            data: val,
            time: Date.now(),
            expired
        }
        localStorage.setItem(key, JSON.stringify(obj));

    },

    /*
    * set 获取方法
    * @ param {String}     key 键
    */
    get(key) {
        let val = localStorage.getItem(key);
        if (!val) {
            return val;
        }
        val = JSON.parse(val);
        if (Date.now() - val.time > val.expired) {
            localStorage.removeItem(key);
            return null
        }

        return val.data;
    },
   /*
    * remove 删除方法
    * @ param {String}     key 键
    */
    remove(key) {
        localStorage.removeItem(key);
    },

}
export default storage;
