<?php

class MaClasse
{
  var $nom;

function __construct($nom)
{
	$this->nom=$nom;
	echo "constructeur : ".$this->nom."<br>";	
}

function __destruct()
{
	echo "destructeur : ".$this->nom."<br>";	
	unset ($this->nom);
}
  
}

$MonObject=new MaClasse('Les �ditions ENI');
echo $MonObject->nom."<br />";
unset ($MonObject);
?>