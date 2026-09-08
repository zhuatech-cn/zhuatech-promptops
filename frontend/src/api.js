/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
export async function api(path,{method='GET',body,user='admin',password}={}){const response=await fetch(path,{method,headers:{'Content-Type':'application/json','Authorization':'Basic '+btoa(user+':'+password)},body:body?JSON.stringify(body):undefined});if(!response.ok)throw new Error('请求失败：'+response.status);return response.json();}
