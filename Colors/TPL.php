
<?php 

    $color=$_GET['search'];
    $url = 'http://www.colourlovers.com/api/palettes/top?format=json';
    $json = file_get_contents($url);
    $array = json_decode($json, true);
for ($ii=0; $ii<20; $ii++)
{
	$title = $array[$ii]['title'];
    echo "<br>name: ".$title."<br>";
for ($i=0; $i<5; $i++)
{
    $code = $array[$ii]["colors"][$i];
    echo "<table border='1' width='100' height='100' style='background-color: #".$code."'>";
    echo "<tr></tr></table>";
}
}
?>
