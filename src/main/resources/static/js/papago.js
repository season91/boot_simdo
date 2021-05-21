(()=>{
    document.querySelector('.translation').addEventListener('click',()=>{
        let url = '/movie/translation';
        let text = document.querySelector('#fmsline').value;
        let lan = document.querySelector('#lang').value;
        let paramObj = new Object();
        paramObj.text = text;
        paramObj.lan = lan;

        let headerObj = new Headers();
        headerObj.append("content-type","application/x-www-form-urlencoded");
        fetch(url,{
            method : "post",
            headers : headerObj,
            body : "data="+JSON.stringify(paramObj)
        }).then(response => {
            if(response.ok){
                return response.text();
            }
            throw new AsyncPageError(response.text());
        }).then((msg) => {
            if(msg == 'fail'){
                alert('번역에 실패했습니다.');
            }else{
                document.querySelector('.fmsline').innerText = msg;
            }
        }).catch(error=>{
            error.alertMessage();
        })
    })
})();