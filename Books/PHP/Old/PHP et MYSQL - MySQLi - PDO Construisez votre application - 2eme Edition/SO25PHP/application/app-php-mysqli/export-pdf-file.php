<?php
require_once("include/config.inc.php");
define('FPDF_FONTPATH','include/font/');
require('include/fpdf.php');

if (isset($_GET['carnetclef'])) $carnetclef = verif_GetPost($_GET['carnetclef']);

class PDF extends FPDF
{

function Header()      
{
    $this->Ln(1);              
}


function Footer()
{
    $this->SetY(-15);           
    $this->SetFont('times','I',8);  
    $this->Cell(0,10,'Page '.$this->PageNo().'/{nb}',0,0,'C');     
}



function tableau($col,$col2)
{
$this->Cell(1,5,$col);
$this->Cell(50);
$this->Cell(0,5,$col2);
$this->Ln(10);         
}

}


$pdf=new PDF();
$pdf->AliasNbPages();
$pdf->AddPage();


$sql="SELECT * FROM carnet WHERE carnetclef='$carnetclef' ";

$qid=mysqli_query($connex, $sql);
if( ! $qid )  die("Probleme :  " . mysqli_error($connex));
$nligne= mysqli_num_rows($qid);
$row=mysqli_fetch_object( $qid);

$pdf->SetFont('times','B',30);
$pdf->Cell(0,5,'FICHE',0,1,'C');

$pdf->Ln(10);          

$pdf->SetFont('times','B',12);
$pdf->Cell(0,5,'Nom : '.$row->nom);
$pdf->Ln(5);         
$pdf->Cell(0,5,'Prenom : '.$row->prenom);
$pdf->Ln(5);         
$pdf->Cell(0,5,'Adresse : '.$row->adresse1);
$pdf->Ln(5);         
$pdf->Cell(20);
$pdf->Cell(0,5,$row->adresse2);
$pdf->Ln(5);         
$pdf->Cell(20);
$pdf->Cell(0,5,$row->codepostal." ".$row->ville);
$pdf->Ln(5);         
$pdf->Rect(10,22,100,35);

$pdf->Ln(10);         
$pdf->MultiCell(100,5,
		"Tel :".$row->tel."\n\rPortable : ".$row->portable."\n\rEmail : ".$row->email."\n\r",
		1,1);

if ($row->photo)
{
	$pdf->Image($destDir.$row->photo,120,20);
}

$pdf->Ln(20);         

$pdf->Cell(20);
$pdf->tableau("Rubrique","Observations");

$sqlDetails="SELECT carnet_details.*,rubrique.id,rubrique.nom 
		     FROM carnet_details, rubrique 
		     WHERE carnet_details.idrubrique=rubrique.id 
		     		AND idcarnet='".$row->id."' ";
$qidDetails=mysqli_query($connex, $sqlDetails);
if (!$qidDetails)  die("Probleme :  " . mysqli_error($connex));

while ($rowDetails=mysqli_fetch_object($qidDetails))
{
$pdf->tableau($rowDetails->nom,stripslashes($rowDetails->observation));

}
$pdf->Ln(5);         

mysqli_free_result($qidDetails);
mysqli_free_result($qid);

mysqli_close($connex);

$pdf->Output();
?>
