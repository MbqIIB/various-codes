Exemple 3.6 : sample.ldif

# D�finir une entr�e de haut niveau
dn: dc=mycompany,dc=com
objectClass: dcObject
objectClass: organization
dc: mycompany
o: Core Java Team

# D�finir une entr�e pour stocker des personnes
# Les recherches d'utilisateurs se basent sur cette entr�e
dn: ou=people,dc=mycompany,dc=com
objectClass: organizationalUnit
ou: people

# D�finir une entr�e utilisateur pour Jean C. Public
dn: uid=jqpublic,ou=people,dc=mycompany,dc=com
objectClass: person
objectClass: uidObject
uid: jqpublic
sn: Public
cn: Jean C. Public
telephoneNumber: +1 408 555 0017
userPassword: wombat

# D�finir une entr�e utilisateur pour Jane Doe
dn: uid=jdoe,ou=people,dc=mycompany,dc=com
objectClass: person
objectClass: uidObject
uid: jdoe
sn: Doe
cn: Jane Doe
telephoneNumber: +1 408 555 0029
userPassword: heffalump

# D�finir une entr�e qui contiendra les groupes LDAP
# Les recherches de r�les se basent sur cette entr�e
dn: ou=groups,dc=mycompany,dc=com
objectClass: organizationalUnit
ou: groups

# D�finir une entr�e pour le groupe "techstaff"
dn: cn=techstaff,ou=groups,dc=mycompany,dc=com
objectClass: groupOfUniqueNames
cn: techstaff
uniqueMember: uid=jdoe,ou=people,dc=mycompany,dc=com

# D�finir une entr�e pour le groupe "staff"
dn: cn=staff,ou=groups,dc=mycompany,dc=com
objectClass: groupOfUniqueNames
cn: staff
uniqueMember: uid=jqpublic,ou=people,dc=mycompany,dc=com
uniqueMember: uid=jdoe,ou=people,dc=mycompany,dc=com
