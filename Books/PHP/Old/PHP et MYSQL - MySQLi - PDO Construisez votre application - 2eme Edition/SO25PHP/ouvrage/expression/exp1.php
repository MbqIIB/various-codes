<?php 
     $chaine = 'Un �diteur de qualit� Editions ENI'; 

     if(preg_match("[Editions]", $chaine)) 
     { 
          echo "Vrai<br>"; 
     } 
     else 
     { 
          echo "Faux<br>"; 
     } 

?>
