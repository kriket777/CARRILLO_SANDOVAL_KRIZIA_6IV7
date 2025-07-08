var mensjae = "HABIA UNA VEZ UN PATITO QUE DECI AMIAU MIAU";
var password = "qwertyuiqwertyui";

var cifrado = CryptoJS.AES.encrypt(mensjae,password);

var descifrado = CryptoJS.AES.encrypt(mensjae,password);

//para que se vea
document.getElementById("demo00").innerHTML = mensjae;
document.getElementById("demo01").innerHTML = cifrado;
document.getElementById("demo02").innerHTML = descifrado;
document.getElementById("demo03").innerHTML = descifrado.toString(CrytoJS.enc.Utf8);


