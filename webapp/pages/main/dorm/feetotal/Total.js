function InitScript(){  
	 this.appKey=""; 
	 this.opts=null;
}
InitScript.prototype={      
   initPage:function(option){
		this.opts= $.extend({},option);   
	}
} 


function changeToTable(targetDatagrid) {
    var tableString = '<table cellspacing="0" class="pb">';
    var frozenColumns = targetDatagrid.datagrid("options").frozenColumns;    // 得到frozenColumns对象
    var columns = targetDatagrid.datagrid("options").columns;    			// 得到columns对象
    var nameList = new Array();

    // 载入title
    if (typeof columns != 'undefined' && columns != '') {
        $(columns).each(function (index) {
            tableString += '\n<tr style="height:40px;">';
            if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
                for (var i = 0; i < frozenColumns[index].length; ++i) {
                    if (!frozenColumns[index][i].hidden && frozenColumns[index][i].checkbox != true) {		// 过滤复选框
                        tableString += '\n<th nowrap="nowrap" width="' + frozenColumns[index][i].width + '"';
                        if (typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
                            tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
                        }
                        if (typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
                            tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
                        }
                        if (typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
                            nameList.push(frozenColumns[index][i]);
                        }
                        tableString += '>' + frozenColumns[0][i].title + '</th>';
                    }
                }
            }
            for (var i = 0; i < columns[index].length; ++i) {
                if (!columns[index][i].hidden) {
                    tableString += '\n<th nowrap="nowrap" width="' + columns[index][i].width + '"';
                    if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
                        tableString += ' rowspan="' + columns[index][i].rowspan + '"';
                    }
                    if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
                        tableString += ' colspan="' + columns[index][i].colspan + '"';
                    }
                    if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != '') {
                        nameList.push(columns[index][i]);
                    }
                    tableString += '>' + columns[index][i].title + '</th>';
                }
            }
            tableString += '\n</tr>';
        });
    }
    // 载入内容
    var rows = targetDatagrid.datagrid("getRows"); // 这段代码是获取当前页的所有行
    for (var i = 0; i < rows.length; ++i) {
    	debugger;
        tableString += '\n<tr style="height:30px;">';
        for (var j = 0; j < nameList.length; ++j) {
            tableString += '\n<td nowrap="nowrap"';
            if (typeof nameList[j].align != 'undefined' && nameList[j].align != '') {
                tableString += ' style="text-align:' + nameList[j].align + ';"';
            }
            tableString += '>';
            tableString += rows[i][nameList[j].field] == undefined ? '': rows[i][nameList[j].field];
            tableString += '</td>';
        }
        tableString += '\n</tr>';
    }
    tableString += '\n</table>';
    return tableString;
}

function exportExcel(strXlsName, exportGrid) {
    var f = $('<form action="' + tsContextPath + '/main/publicUtilService/stream/exportExcel.do" method="post" id="fm1"></form>');
    var i = $('<input type="hidden" id="excelContent" name="excelContent" />');
    var l = $('<input type="hidden" id="excelName" name="excelName" />');
    i.val(changeToTable(exportGrid));
    i.appendTo(f);
    l.val(strXlsName);
    l.appendTo(f);
    f.appendTo(document.body).submit();
    document.body.removeChild(f);
}


function queryTotal() {
	if ($("#yearMonth").val() == "") {
		$.messager.alert("警告", "选择月份不能为空!");
		return false;
	}
	
	var params = {
			yearMonth : $("#yearMonth").val()
	};
//	$.ajax({
//		dataType:'json',
//		cache:false,
//		url:tsContextPath + '/main/feeTotalService/json/queryTotal.do?timeStamp=' + (new Date()).getTime(),
//		data:params,
//		success:function(data, textStatus){
//			
//		},
//		error:function(XmlHttpRequest, textStatus, errorThrown){   
//			$("#roomPanel").html('');
//			var str = XmlHttpRequest.responseText; 
//			if($.ts.Utils.isEmpty(str)){
//				str=XmlHttpRequest.responseXML;
//			}
//			str=str+"<hr/>"+textStatus;
//			$.ts.EasyUI.showContentDialog(str); 
//		} 
//	});
	
	var params = {
		yearMonth : $("#yearMonth").val()
	};
	$("#dg").datagrid('options').url = tsContextPath + '/main/feeTotalService/json/queryTotal.do?timeStamp=' + (new Date()).getTime();
	$("#dg").datagrid('reload',params);
	return true;
	
}