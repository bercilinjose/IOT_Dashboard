<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <title>WMD Device - Notification</title>
    <style>
    table, th, td {
	  border: 2px solid black !important;
	  border-collapse: collapse;
	}
	body{
	font-family:Monaco,Menlo,Consolas,"Courier New",monospace !important;
	}
    </style>
</head>
<body>
<div style="margin-top: 10px">
<br/>
<div class="container-fluid">
<h4>Hello ${username},</h4><br>
<pre style="border: 0 !important;background:transparent !important;">
${content}
</pre>
</div>
</div>
</div>
<br/>
</body>
</html>