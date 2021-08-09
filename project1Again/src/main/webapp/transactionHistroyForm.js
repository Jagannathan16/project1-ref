var typeOfHistory = document.getElementById("choose");
var lastSomeMonth = document.getElementsByClassName("month-form")[0];
var from = document.getElementsByClassName("from-only-form")[0];
var fromTo = document.getElementsByClassName("from-to-form")[0];

typeOfHistory.addEventListener('change',event=>{
    var type = event.target.value;
    console.log(type);
    if(type == "last-some-month"){
        lastSomeMonth.classList.add("month-form-show");
        fromTo.classList.remove("from-to-form-show");
        from.classList.remove("from-only-form-show");
        console.log("some month")
    }else if(type == "from-only"){
        from.classList.add("from-only-form-show");
        lastSomeMonth.classList.remove("month-form-show");
        fromTo.classList.remove("from-to-form-show");
    }else if(type == "from-to"){
        fromTo.classList.add("from-to-form-show");
        lastSomeMonth.classList.remove("month-form-show");
        from.classList.remove("from-only-form-show");
        console.log("from to")
    }else{
        lastSomeMonth.classList.remove("month-form-show");
        from.classList.remove("from-only-form-show");
        fromTo.classList.remove("from-to-form-show");
    
    }
})