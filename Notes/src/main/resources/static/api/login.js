function loginApi(data) {
  return $axios({
    'url': '/employee/login',
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    'url':'/employee/logout',
    'method': 'post',
  })
}
function registerApi(data){
  return $axios({
    'url': '/employee/logon',
    'method': 'post',
    data
  })
}


