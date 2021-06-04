import axios from 'axios';

const instance = axios.create({
    baseURL: "http://13.125.164.214:9090",
});

instance.interceptors.request.use(function (config) {
  if ( localStorage.getItem("token") !== null) {
    config['headers'] = {
      Authorization: `Bearer ${localStorage.getItem("token")}`
    };
  }

  return config;
});

// instance.interceptors.response.use(function (response) {
//   store.commit('error/setValidationError', {});

//   return response;
// }, function (error) {
//   if (error.response.status === 422) {
//     store.commit('error/setValidationError', error.response.data.data);
//   } else {
//     return Promise.reject(error);
//   }
// });

export default instance;