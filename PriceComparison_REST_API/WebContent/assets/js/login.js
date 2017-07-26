var rFormStyle      = document.getElementById('register-form').style;
var lFormStyle      = document.getElementById('login-form').style;
var regButton		= document.getElementById('registerNewUserBtnContainer').style;
var logButton 		= document.getElementById('loginBtnContainer').style;

function swapToRegisterForm()
{
    var email = document.getElementById('loginInputEmail')
    document.getElementById('registerInputEmail').value = '';
    
    if (email.value != '' || email.value != null)
    {
        document.getElementById('registerInputEmail').value = email.value;
    }
    
    lFormStyle.display  = 'none';
	regButton.display 	= 'none';
    rFormStyle.display  = 'block';
	logButton.display 	= 'block';
}

function swapToLoginForm()
{    
    var email = document.getElementById('registerInputEmail')
    document.getElementById('loginInputEmail').value = '';
    
    if (email.value != '' || email.value != null)
    {
        document.getElementById('loginInputEmail').value = email.value;
    }
    
    rFormStyle.display  = 'none';
	logButton.display 	= 'none';
    lFormStyle.display  = 'block';
	regButton.display 	= 'block';
}