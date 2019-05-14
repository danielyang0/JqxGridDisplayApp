<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'>In this sample is demonstrated how to display aggregates in jqxGrid.</title>
    <meta name="description" content="JavaScript Grid which has multiple built-in aggregates" />    
    <link rel="stylesheet" href="js/jqwidgets/styles/jqx.base.css" type="text/css" />
    <link rel="stylesheet" href="css/index.css" type="text/css" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1 minimum-scale=1" />  
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
    <META HTTP-EQUIV="expires" CONTENT="0">
    <script type="text/javascript" src="js/scripts/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.edit.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.aggregates.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="js/scripts/demos.js"></script>
</head>
<body class='default'>
   
    <div id='jqxWidget'>
        <div id="grid"></div>
    </div>
    <button class="btn" id="uploadBtn">Upload</button>
    <button class="btn" id="newRowBtn">newRow</button>
    <button class="btn" id="deleteRowBtn">deleteRow</button>
    <button class="btn" id="deleteAllBtn">deleteAll</button>
    <button class="btn" id="refreshBtn">refresh</button>
    <button class="btn" id="addATestRecord">addTestRecord</button>
    
    <script src="js/index.js"></script>
    <script type="text/javascript">
        $(document).ready(refresh());
    </script>
    
</body>
</html>
