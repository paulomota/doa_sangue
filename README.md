# doa sangue

##Rotas existentes:

###(GET) /login?email=xxx&password=XXX

###(GET) /login/retrieve-password?email=XXX

###(POST) /user/register
Parametro JSON User

###(POST) /user/update
Parametro JSON User

Ex JSON User

{"id":1,"name":"Paulo Mota","email":"nomedapessoa@gmail.com","password":"e10adc3949ba59abbe56e057f20f883e","lat":null,"lng":null}

###(POST) /users/update-picture/{userId}
Content-type: application/x-www-form-urlencoded

FormParam: pictureUrl (String)

###(POST) /users/update-picture (Em teste, aqui envia a imagem de fato)
Headers:
Content-type: multipart/form-data; boundary=Nounce
Accept-Encoding: multipart/form-data

Parametro JSON do tipo MultipartFormDTO exemplo: 
{
email: "emaildousuario@email.com",
filedata: *aqui o array de bytes da imagem* 
}

###(GET) /city
Retorna a lista de cidades cadastradas

###(GET) /city?state=DF
Retorna a lista de cidades cadastradas para o estado informado
O parametro é a sigla do estado, sempre com duas letras

###(GET) /blood-type
Retorna a lista de tipos sanguineos

###(GET) /receiver/{userId}
Retorna uma lista de usuarios recebedores para o usuario do id informado,
hoje o tamanho máximo dessa lista é de 12 itens

Response: JsonArray de Users

###(POST) /donation
Content-type: application/x-www-form-urlencoded

Parametros:
donorId - (long) id do usuario que está doando
receiverId - (long) id do usuario que está recebendo
match - (boolean) opcao do doador para com o recebedor

