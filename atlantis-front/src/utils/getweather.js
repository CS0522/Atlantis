import axios from 'axios'

const key_getWeather = 'fcfbdfc3bdbcd13bde6ff8372f635f4e';

// get city's addcode
async function getCityAdcode(cityName) {
    const params = {
        key: key_getWeather,
        address: cityName,
    };
    let res = await axios.get('https://restapi.amap.com/v3/geocode/geo?parameters', {params});
    // console.log('City adcode: ', res.data)
    return res.data.geocodes[0].adcode
}

export async function getWeather(cityName) {
    const adcode = await getCityAdcode(cityName);
    const params = {
        key: key_getWeather,
        city: adcode,
        extensions: 'base'
    };
    let res = await axios.get('https://restapi.amap.com/v3/weather/weatherInfo?parameters', {params});
    // console.log('Local weather: ', res.data.lives[0])
    let result = {
        province: res.data.lives[0].province,
        city: res.data.lives[0].city,
        weather: res.data.lives[0].weather,
        temperature: res.data.lives[0].temperature,
        winddirection: res.data.lives[0].winddirection,
        windpower: res.data.lives[0].windpower,
        humidity: res.data.lives[0].humidity
    }
    // console.log(result)
    return res.data.lives[0];
}

