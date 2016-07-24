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

###(POST) /user/update-geolocation/{userId}
Content-type: application/x-www-form-urlencoded

FormParam: lat (String), lng (String), city (String)

###(POST) /user/update-role/{userId}/{roleInitial}
Content-type: application/x-www-form-urlencoded

roleInitial pode ser: R (receiver) ou D (donor)

###(POST) /user/update-picture/{userId}
Content-type: application/x-www-form-urlencoded

FormParam: pictureUrl (String)

###(POST) /user/update-receiver-info/{receiverId}
Content-type: application/x-www-form-urlencoded

FormParam: hospital (String), urgency (String), reason (String)

###(POST) /user/update-device-token
Content-type: application/x-www-form-urlencoded

FormParam: userId (long), deviceToken (String)

###(POST) /chat
Content-type: application/x-www-form-urlencoded

FormParam: senderId (long), receiverId (long), message (String)

OBS: O usuario destinatario da mensagem (receiverId) precisa ter o deviceToken cadastrado

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

###(GET) /about
Retorna o texto da sessão sobre do app

