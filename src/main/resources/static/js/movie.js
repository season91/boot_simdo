(()=>{
    let wishcolor = document.querySelector('.wish').style.color;
    let mvno = "?mvNo="+document.querySelector('.mvno').value;
    console.dir(wishcolor);

    let wishAdd = ()=>{
        let headerObj = new Headers();
        headerObj.append('content-type', "application/x-www-form-urlencoded");

        fetch("/wish/add"+mvno,{
            method:"get",
            header : headerObj
        }).then(response => {
            if(response.ok){
                return response.text();
            }
        })
    }

    let wishDel = ()=>{
        let headerObj = new Headers();
        headerObj.append('content-type', "application/x-www-form-urlencoded");

        fetch("/wish/del"+mvno,{
            method:"get",
            header : headerObj
        }).then(response => {
            if(response.ok){
                return response.text();
            }
        })
    }

    let wishchange = (changecolor)=>{
        let wish = document.querySelector('.wish');
        wish.style.color = changecolor;
        wishcolor = changecolor;

    }


    document.querySelector('.wish').addEventListener('click',()=>{
        if(wishcolor == ''){
            wishAdd();
            wishchange('red');

        } else {
            wishDel();
            wishchange('');

        }
    })


})();