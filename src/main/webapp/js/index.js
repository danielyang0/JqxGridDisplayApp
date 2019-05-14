
data = [];//the original data to be displayed in the table
curselect = -1;//currently selected row index

/**
 * get newest data from the server and refresh the data in page
 * @returns
 */
function refresh() {
	$.ajax({  
		url : '/refresh',  
		type : 'GET',  
	}).done(function(res) {
		data = res;
		showTable();
	}).fail(function(res) {
		"not s"
	});
}

/**
 * add a new row into the table and post it to server
 * @param postData
 * @returns
 */
function addRow(postData){
	$.ajax({  
		url : '/addRow',  
		type : 'POST',  
		data : JSON.stringify( postData ),  
		contentType : "application/json;charset=utf-8",
		dataType: 'json'
	}).done(function(res) {
		console.log(res);
	}).fail(function(res) {
		console.log(res);
	});
}

/**
 * update a row to the server
 * @param postData
 * @returns
 */
function updateRow(postData) {
	$.ajax({  
		url : '/updateRow',  
		type : 'POST',  
		data : JSON.stringify( postData ),  
		contentType : "application/json;charset=utf-8",
		dataType: 'json'
	}).done(function(res) {
		console.log(res);
	}).fail(function(res) {
		console.log(res);
		refresh();
	});
}

/**
 * update a row with id changed to the server
 * @param postData
 * @param id
 * @returns
 */
function updateRowWithId(postData, id) {
	console.log(postData);
	$.ajax({  
		url : '/updateRow/'+id,  
		type : 'POST',  
		data : JSON.stringify( postData ),  
		contentType : "application/json;charset=utf-8",
		dataType: 'json'
	}).done(function(res) {
		console.log(res);
	}).fail(function(res) {
		console.log(res);
		refresh();
	});
}

/**
 * display data in the JqxGrid window
 * @returns
 */
function showTable() {
	// prepare the data
	var source =
	{
		localdata: data,
		datatype: "array",
		datafields:
			[
				{ name: 'passengerId', type: 'number' },
				{ name: 'survived', type: 'string' },
				{ name: 'pclass', type: 'string' },
				{ name: 'name', type: 'string' },
				{ name: 'sex', type: 'string' },
				{ name: 'age', type: 'string' },
				{ name: 'sibSp', type: 'string' },
				{ name: 'parch', type: 'string' },
				{ name: 'ticket', type: 'string' },
				{ name: 'fare', type: 'string' },
				{ name: 'cabin', type: 'string' },
				{ name: 'embarked', type: 'string' }
			],
		updaterow: function (rowid, rowdata) {
			// synchronize with the server - send update command
			if (data[rowid].passengerId != rowdata.passengerId) {
				//if the user is modifying passengerId
				updateRowWithId(rowdata, data[rowid].passengerId);
			} else {
				updateRow(rowdata);
			}
		}
	};

	var dataAdapter = new $.jqx.dataAdapter(source);

	// initialize jqxGrid
	$("#grid").jqxGrid(
		{
			width: 1265,//getWidth('Grid')
			height: 700,
			source: dataAdapter,     
			editable: true,
			selectionmode: 'singlecell',
            adaptive: true,
			columns: [
				{ text: 'PassengerId', columntype: 'textbox', datafield: 'passengerId', width: 100 },
				{ text: 'Survived', datafield: 'survived', columntype: 'textbox', width: 80 },
				{ text: 'Pclass', datafield: 'pclass', width: 60, columntype: 'textbox'},
				{ text: 'Name', datafield: 'name', columntype: 'textbox', width: 250},
				{ text: 'Sex', datafield: 'sex', columntype: 'textbox', width: 85},
				{ text: 'Age', datafield: 'age', columntype: 'textbox', width: 40},
				{ text: 'SibSp', datafield: 'sibSp', columntype: 'textbox', width: 100},
				{ text: 'Parch', datafield: 'parch', columntype: 'textbox', width: 100},
				{ text: 'Ticket', datafield: 'ticket', columntype: 'textbox', width: 100},
				{ text: 'Fare', datafield: 'fare', columntype: 'textbox', width: 100},
				{ text: 'Cabin', datafield: 'cabin', columntype: 'textbox', width: 100},
				{ text: 'Embarked', datafield: 'embarked', columntype: 'textbox', width: 100}
			]
		});
	$("#grid").on('cellselect', function (event) {
		curselect = event.args.rowindex;
    });
}


//binding click event *********************

//refresh page
$('#refreshBtn').on('click', function() {
	refresh();
});

//upload csv file from local
$('#uploadBtn').on('click', function(){
	let formData = new FormData()
	let input = document.createElement('input')
	input.setAttribute('type', 'file')
	input.setAttribute('name', 'sheet')
	input.click()
	input.onchange = function () {
		formData.append('file', input.files[0])
		$.ajax({  
			url : '/upload',  
			type : 'POST',  
			data : formData,  
			processData : false, 
			contentType : false 
		}).done(function(res) {
			console.log(res);
			data = res;
			showTable();
		}).fail(function(res) {
			console.log(res);
		}); 
	}
});


//add a preset test data to the server but not refresh the page
$('#addATestRecord').on('click', function(){
	var nextId = 0;
	for (var i = 0; i < data.length; ++i) {
		if (data[i].passengerId > nextId) {
			nextId = data[i].passengerId;
		}
	}
	nextId += 1;
	var newRow = {age: "32",
			cabin: "A001",
			embarked: "T",
			fare: "11.22",
			name: "Robert",
			parch: "6",
			passengerId: nextId,
			pclass: "1",
			sex: "male",
			sibSp: "0",
			survived: "0",
			ticket: "XXX 123"};
	addRow(newRow);
});


//add a new row and sync with the server
$('#newRowBtn').on('click', function(){
	var nextId = 0;
	for (var i = 0; i < data.length; ++i) {
		if (data[i].passengerId > nextId) {
			nextId = data[i].passengerId;
		}
	}
	nextId += 1;
	var newRow = {age: "",
			cabin: "",
			embarked: "",
			fare: "",
			name: "",
			parch: "",
			passengerId: nextId,
			pclass: "",
			sex: "",
			sibSp: "",
			survived: "",
			ticket: ""};
	data.push(newRow);
	addRow(newRow);
	showTable();
});

//delete a row
$('#deleteRowBtn').on('click', function() {
	if (curselect == -1) {
		alert("Please select a row before deleting");
		return;
	}
	//remove a row
	var newdata = [];
	for (var i = 0; i < data.length; i++) {
		if (i !== curselect) {
			newdata.push(data[i]);
		}
	}
	//delete from server
	$.ajax({
		url: '/deleteRow/'+data[curselect].passengerId,
		type: 'GET'
	}).done(function (res) {
		console.log(res);
	}).fail(function (res) {
		console.log(res);
	});
	data = newdata;
	showTable();
});

//delete all of the data in the table 
$('#deleteAllBtn').on('click', function() {
	//delete from server
	$.ajax({
		url: '/deleteAll',
		type: 'GET'
	}).done(function (res) {
		console.log(res);
		data = [];
		curselect = -1;
		showTable();
	}).fail(function (res) {
		console.log(res);
	});
});


