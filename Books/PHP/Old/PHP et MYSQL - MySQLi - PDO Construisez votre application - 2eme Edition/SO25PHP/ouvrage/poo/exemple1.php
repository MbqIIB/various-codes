<?php

class MaClasse
{
   public $nom = 'Les �ditions ENI';

   public function afficheNom()
   {
	   	echo $this->nom;
   }	
}

$MonObject=new MaClasse();
$MonObject->afficheNom();
unset ($MonObject);

?>