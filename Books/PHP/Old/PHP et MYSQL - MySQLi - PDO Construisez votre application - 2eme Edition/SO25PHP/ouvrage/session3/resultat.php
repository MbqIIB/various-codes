<?php
session_start();

if (time()-$_SESSION['dernier_passage']>$_SESSION['duree']) 
{
echo "Session expir�e"; 
exit();
}
else
{
	$_SESSION['dernier_passage'] = time() ;
	echo "Session... toujours valide";
}
?>