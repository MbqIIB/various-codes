<?php
$chaine = 'Un �diteur de qualit� Edition ENI'; 
$expression="[Edition]";
if(filter_var($chaine, FILTER_VALIDATE_REGEXP, 
              array("options"=>array("regexp" => $expression))) !== false)
	echo "Vrai";
else
	echo "Faux";
   
?> 