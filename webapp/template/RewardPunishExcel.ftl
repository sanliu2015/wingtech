<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2016-12-01T06:42:01Z</LastSaved>
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
  <ActiveSheet>1</ActiveSheet>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s64" ss:Name="常规_邮件_1">
   <Alignment ss:Vertical="Center"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="9"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s67">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="9" ss:Color="#000000"
    ss:Bold="1"/>
   <Interior ss:Color="#92D050" ss:Pattern="Solid"/>
   <NumberFormat/>
  </Style>
  <Style ss:ID="s80">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="9" ss:Color="#000000"
    ss:Bold="1"/>
   <Interior ss:Color="#92D050" ss:Pattern="Solid"/>
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s81" ss:Parent="s64">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="9" ss:Color="#000000"
    ss:Bold="1"/>
   <Interior ss:Color="#92D050" ss:Pattern="Solid"/>
   <NumberFormat/>
  </Style>
 </Styles>
 <Worksheet ss:Name="处罚">
  <Table ss:ExpandedColumnCount="12" ss:ExpandedRowCount="${totalRowHdr+2}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Column ss:AutoFitWidth="0" ss:Width="54.75"/>
   <Column ss:Index="9" ss:AutoFitWidth="0" ss:Width="66"/>
   <Row ss:AutoFitHeight="0" ss:Height="24.9375">
    <Cell ss:StyleID="s80"><Data ss:Type="String">日期</Data></Cell>
    <Cell ss:StyleID="s80"><Data ss:Type="String">编号</Data></Cell>
    <Cell ss:StyleID="s80"><Data ss:Type="String">劳务派遣</Data></Cell>
    <Cell ss:StyleID="s80"><Data ss:Type="String">公司</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">部门</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">科室</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">姓名</Data></Cell>
    <Cell ss:StyleID="s81"><Data ss:Type="String">工号</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">处罚原因</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">经济处罚</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">行政处罚</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">备注</Data></Cell>
   </Row>
   <#if (dataListHdr?size>0)>
   <#list dataListHdr as hdrObj>
   <Row>
    <Cell><Data ss:Type="String">${hdrObj.occurDate}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.number}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.labour!''}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.company!''}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.parentDepName!''}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.depName!''}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.empName!''}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.empNumber!''}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.reason!''}</Data></Cell>
    <Cell><Data ss:Type="Number">${hdrObj.amount!''}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.executivePunish!''}</Data></Cell>
    <Cell><Data ss:Type="String">${hdrObj.description!''}</Data></Cell>
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
   <Panes>
    <Pane>
     <Number>3</Number>
     <RangeSelection>R1C1:R2C14</RangeSelection>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
  <DataValidation xmlns="urn:schemas-microsoft-com:office:excel">
   <Range>R1C10:R1C11</Range>
   <Qualifier>GreaterOrEqual</Qualifier>
  </DataValidation>
 </Worksheet>
 <Worksheet ss:Name="奖励">
  <Table ss:ExpandedColumnCount="11" ss:ExpandedRowCount="${totalRowDtl+2}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="13.5">
   <Row ss:AutoFitHeight="0" ss:Height="24.9375">
    <Cell ss:StyleID="s80"><Data ss:Type="String">日期</Data></Cell>
    <Cell ss:StyleID="s80"><Data ss:Type="String">编号</Data></Cell>
    <Cell ss:StyleID="s80"><Data ss:Type="String">劳务派遣</Data></Cell>
    <Cell ss:StyleID="s80"><Data ss:Type="String">公司</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">部门</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">科室</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">姓名</Data></Cell>
    <Cell ss:StyleID="s81"><Data ss:Type="String">工号</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">奖励原因</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">经济奖励</Data></Cell>
    <Cell ss:StyleID="s67"><Data ss:Type="String">备注</Data></Cell>
   </Row>
   <#if (dataListDtl?size>0)>
   <#list dataListDtl as dtlObj>
   <Row>
    <Cell><Data ss:Type="String">${dtlObj.occurDate}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.number}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.labour!''}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.company!''}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.parentDepName!''}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.depName!''}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.empName!''}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.empNumber!''}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.reason!''}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.amount!''}</Data></Cell>
    <Cell><Data ss:Type="String">${dtlObj.description!''}</Data></Cell>
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
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>6</ActiveRow>
     <ActiveCol>8</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
  <DataValidation xmlns="urn:schemas-microsoft-com:office:excel">
   <Range>R1C10</Range>
   <Qualifier>GreaterOrEqual</Qualifier>
  </DataValidation>
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
