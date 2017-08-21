<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2016-09-06T10:00:24Z</LastSaved>
  <Version>14.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>7980</WindowHeight>
  <WindowWidth>14805</WindowWidth>
  <WindowTopX>240</WindowTopX>
  <WindowTopY>135</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s16" ss:Name="常规_Sheet1_3">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s17" ss:Parent="s16">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="9" ss:Bold="1"/>
   <Interior ss:Color="#FF8080" ss:Pattern="Solid"/>
   <NumberFormat/>
  </Style>
  <Style ss:ID="s19" ss:Parent="s16">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="9" ss:Bold="1"/>
   <Interior ss:Color="#FF8080" ss:Pattern="Solid"/>
   <NumberFormat ss:Format="0.00_);[Red]\(0.00\)"/>
  </Style>
  <Style ss:ID="s20">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="9"/>
   <Interior/>
   <NumberFormat/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="14" ss:ExpandedRowCount="${totalRow+2}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Row ss:Height="22.5">
    <Cell ss:StyleID="s17"><Data ss:Type="String">入住&#10;单位</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">部门</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">科室</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">职位</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">姓名</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">工号</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">劳务公司</Data></Cell>
    <Cell ss:StyleID="s19"><Data ss:Type="String">人均水电气费</Data></Cell>
    <Cell ss:StyleID="s19"><Data ss:Type="String">共摊水电费</Data></Cell>
    <Cell ss:StyleID="s19"><Data ss:Type="String">房租扣款</Data></Cell>
    <Cell ss:StyleID="s19"><Data ss:Type="String">物品损坏扣款</Data></Cell>
    <Cell ss:StyleID="s19"><Data ss:Type="String">奖励合计</Data></Cell>
    <Cell ss:StyleID="s19"><Data ss:Type="String">惩罚合计</Data></Cell>
    <Cell ss:StyleID="s19"><Data ss:Type="String">合计</Data></Cell>
   </Row>
 <#if (dataList?size>0)>
 <#list dataList as dtl>
   <Row ss:AutoFitHeight="0" ss:Height="20.0625">
    <Cell ss:StyleID="s20"><Data ss:Type="String">${dtl.orgName!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="String">${dtl.parentDepName!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="String">${dtl.depName!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="String">${dtl.jobName!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="String">${dtl.name!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="String">${dtl.number!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="String">${dtl.labourCorName!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="Number">${dtl.wtpwgsFee!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="Number">${dtl.sharedFee!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="Number">${dtl.paidRoomFee!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="Number">${dtl.damageFee!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="Number">${dtl.rewardFee!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="Number">${dtl.punishFee!''}</Data></Cell>
    <Cell ss:StyleID="s20"><Data ss:Type="Number">${dtl.totalFee!''}</Data></Cell>
   </Row>
</#list>
</#if>    
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>5</ActiveRow>
     <ActiveCol>13</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 <Worksheet ss:Name="Sheet2">
  <Table ss:ExpandedColumnCount="1" ss:ExpandedRowCount="1" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 <Worksheet ss:Name="Sheet3">
  <Table ss:ExpandedColumnCount="1" ss:ExpandedRowCount="1" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
