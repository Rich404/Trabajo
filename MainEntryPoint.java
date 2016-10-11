/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yum.e3.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.Window; 
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Main entry point.
 *
 * @author richie
 */
public class MainEntryPoint implements EntryPoint {

    /**
     * Creates a new instance of MainEntryPoint*/
  
    
    
    public ListGrid NetGrid = new ListGrid(); 
    
    SectionStackSection NetDetailsSection = new SectionStackSection("Information Network Details");
    SectionStackSection GridSection = new SectionStackSection("Information Network"); 
    ArrayList SubnetPk = new ArrayList();
    
    /**
     * Creates a new instance of MainEntryPoint
     */
    public void onModuleLoad()
	{
            
           
            
        
            
            
            setLayout();
            //RootPanel.get().add(LblTitle);
           // RootPanel.get().setWidgetPosition(Ap,300,50);
	}
    
  
    public Record FillPlus (String subnetextract,String subnetpart)
    {
        
       int[] intsubnet =  new int[14];
       int tpvs = Integer.parseInt(subnetextract) + 15 - Integer.parseInt(subnetextract) + 25;
        for(int i=1;i<=14;i++)
        {
            intsubnet[i] = Integer.parseInt(subnetextract) + i;
           // subsuma[i] = String.valueOf(intsubnet);
           // Window.alert(String.valueOf(intsubnet[i]) + "Indice " + i);
        }
        Record record= new Record();
        record.setAttribute("Router",subnetpart+intsubnet[1]);
        record.setAttribute("Gerencial",subnetpart+intsubnet[2]);
        record.setAttribute("LaserPrint",subnetpart+intsubnet[3]);
        record.setAttribute("FingerPrint",subnetpart+intsubnet[4]);
        record.setAttribute("Training",subnetpart+intsubnet[5]);
        record.setAttribute("Aloha_QSR",subnetpart+intsubnet[6]);
        record.setAttribute("Timer1",subnetpart+intsubnet[7]);
        record.setAttribute("Timer2",subnetpart+intsubnet[8]);
        record.setAttribute("TelIp",subnetpart+intsubnet[9]);
        record.setAttribute("PLC",subnetpart+intsubnet[10]);
        record.setAttribute("CamarasIp",subnetpart+intsubnet[11]);
        record.setAttribute("KitchenBrain1",subnetpart+intsubnet[12]);
        record.setAttribute("KitchenBrain2",subnetpart+intsubnet[13]);
        record.setAttribute("KitchenBrain3",subnetpart+intsubnet[14]);
        record.setAttribute("TPVs",subnetpart+tpvs);
        
        return record;
    }
   
    public ListGridRecord Row (String subnet,int cc,String name,String brand,String netmask,String dmvpnip,String vlan100ip,String vlan101ip)
    {
        ListGridRecord Row =  new ListGridRecord();
        Row.setAttribute("SubNet",subnet);
        Row.setAttribute("CC",cc);
        Row.setAttribute("Name",name);
        Row.setAttribute("Brand",brand);
        Row.setAttribute("NetMask",netmask);
        Row.setAttribute("DmvpnIp",dmvpnip);
        Row.setAttribute("Vlan100Ip", vlan100ip);
        Row.setAttribute("Vlan101Ip", vlan101ip);
        
        return Row;
    }
    
    
    public void setLayout()
    {
 
        
        /*********************************/
        
 
   final DetailViewer printViewer = new DetailViewer();
   final DetailViewer prinViewerPlus = new DetailViewer();
     
    ListGridField Lfsubnet = new ListGridField("SubNet");
   ListGridField  Lfcc = new ListGridField("CC");
    ListGridField Lfname = new ListGridField("Name");     
   ListGridField  Lfbrand = new ListGridField("Brand");    
    ListGridField Lfnetmask = new ListGridField("NetMask");  
   ListGridField Lfdmvpn_ip= new ListGridField("DmvpnIp");   
    ListGridField Lfvlan100_ip = new ListGridField("Vlan100Ip");
    ListGridField Lfvlan101_ip = new ListGridField("Vlan101Ip");
    
    DetailViewerField Dvsubnet = new DetailViewerField ("SubNet");
    DetailViewerField  Dvcc = new DetailViewerField ("CC");
    DetailViewerField Dvname = new DetailViewerField ("Name");
    DetailViewerField  Dvbrand = new DetailViewerField ("Brand");
    DetailViewerField Dvnetmask = new DetailViewerField ("NetMask");
    DetailViewerField Dvdmvpn_ip= new DetailViewerField ("DmvpnIp");
    DetailViewerField Dvvlan100_ip = new DetailViewerField ("Vlan100Ip");
    DetailViewerField Dvvlan101_ip = new DetailViewerField ("Vlan101Ip");
    
    DetailViewerField Dvrouter =  new DetailViewerField("Router");
    DetailViewerField Dvgerencial =  new DetailViewerField("Gerencial");
    DetailViewerField Dvlaserprint =  new DetailViewerField("LaserPrint");
    DetailViewerField Dvfingerprint =  new DetailViewerField("FingerPrint");
    DetailViewerField Dvtraining = new DetailViewerField("Training");
    DetailViewerField Dvaloha_qsr =  new DetailViewerField("Aloha_QSR");
    DetailViewerField Dvtimer1 = new DetailViewerField("Timer1");
    DetailViewerField Dvtimer2 = new DetailViewerField("Timer2");
    DetailViewerField Dvtelip = new DetailViewerField("TelIp");
    DetailViewerField Dvplc = new DetailViewerField("PLC");
    DetailViewerField Dvcamarasip =  new DetailViewerField("CamarasIp");
    DetailViewerField Dvkitchenbrain1 =  new DetailViewerField("KitchenBrain1");
    DetailViewerField Dvkitchenbrain2 =  new DetailViewerField("KitchenBrain2");
    DetailViewerField Dvkitchenbrain3 =  new DetailViewerField("KitchenBrain3");
    DetailViewerField Dvtpvs =  new DetailViewerField("TPVs");
  
    List<ListGridRecord> Rows = new ArrayList<ListGridRecord>(); 
    
     Rows.add(Row("192.168.2.16" , 1010 , "PLAZA JUAREZ" , "PH" , "225.225.225.6" , "192.168.4.16 ", "192.168.4.160 ","192.168.4.61"));
     Rows.add(Row("192.168.4.50" , 1010 , "ALAMEDAS" , "KFC" , "225.225.225.6" , "192.168.4.12 ", "192.168.4.55 ","192.168.4.71"));
     SubnetPk.add("192.168.2.16");
     SubnetPk.add("192.168.4.50");
      
  
  
        SectionStack printStack = new SectionStack();  
        printStack.setVisibilityMode(VisibilityMode.MULTIPLE);  
        printStack.setWidth(400);  
        printStack.setHeight(700);  
  
        //printViewer.setData(Rows.toArray(new ListGridRecord[0]));
        printViewer.setFields(new DetailViewerField[] {Dvsubnet,Dvcc,Dvname,Dvbrand,Dvnetmask,Dvdmvpn_ip,Dvvlan100_ip,Dvvlan101_ip});
        printViewer.setWidth100();
        printViewer.setMargin(10);
        
        //prinViewerPlus.setData(data);
       //prinViewerPlus.setData(Rows.toArray(new ListGridRecord[0]));
       prinViewerPlus.setFields(new DetailViewerField[] {Dvrouter,Dvgerencial,Dvlaserprint,Dvfingerprint,Dvtraining,Dvaloha_qsr,Dvtimer1,Dvtimer2,Dvtelip,Dvplc,Dvplc,Dvcamarasip,Dvkitchenbrain1,Dvkitchenbrain2,Dvkitchenbrain3,Dvtpvs});
       prinViewerPlus.setWidth100();
       prinViewerPlus.setMargin(10);
  
       
       
        NetGrid.setData(Rows.toArray(new ListGridRecord[0]));  
        NetGrid.setFields(new ListGridField[] {Lfsubnet,Lfcc,Lfname,Lfbrand,Lfnetmask,Lfdmvpn_ip,Lfvlan100_ip,Lfvlan101_ip});
        NetGrid.setCanResizeFields(true);
        NetGrid.setHeight(150); 
       
       
  
        NetGrid.addRecordClickHandler(new RecordClickHandler() 
        {  
            public void onRecordClick(RecordClickEvent event)
            {  
               
              printViewer.setData(new Record[]{event.getRecord()});
              
              int iclmn = event.getFieldNum();
              int irow =  event.getRecordNum();
              
              
              
              String  subnetfull = String.valueOf(SubnetPk.get(irow));
              int x = subnetfull.length() - 2;
              String subextract = subnetfull.substring(x,subnetfull.length());
             //SC.say(String.valueOf(iclmn) + String.valueOf(irow) );
             //SC.say(subnetfull.substring(0,10));
           
              prinViewerPlus.setData(new Record[]{FillPlus(subextract,subnetfull.substring(0,10))});
              
              
              
    
              
              
            }  
        });
       
  
         
        GridSection.setExpanded(true);  
        GridSection.addItem(NetGrid);  
        printStack.addSection(GridSection);  
  
         
        NetDetailsSection.setExpanded(true);
        NetDetailsSection.addItem(printViewer);
        NetDetailsSection.addItem(prinViewerPlus);
        printStack.addSection(NetDetailsSection);
        
  
        
        
       /* NetDetailsSectionPlus.setExpanded(true);
        
        printStack.addSection(NetDetailsSectionPlus);*/
        
        
        final VLayout printContainer = new VLayout(); 
        HLayout printButtonLayout = new HLayout();  
  
        
        
        
        // Add Fields to formDynamic
         final TextItem txtSubnet = new TextItem();  
         txtSubnet.setTitle("Subnet");
         final TextItem txtCC = new TextItem();
         txtCC.setTitle("CC");
         final TextItem txtName = new TextItem();
         txtName.setTitle("Name");
         final TextItem txtBrand= new TextItem();
         txtBrand.setTitle("Brand");
         final TextItem txtNetMask= new TextItem();
         txtNetMask.setTitle("NetMask");
         final TextItem txtDmvpnIp= new TextItem();
         txtDmvpnIp.setTitle( "DmvpnIp");
         final TextItem txtVlan100Ip = new TextItem();
         txtVlan100Ip.setTitle( "Vlan100Ip");
         final TextItem txtVlan101Ip = new TextItem();
         txtVlan101Ip.setTitle( "Vlan101Ip");
          
        
        
        
        IButton BtnInfNet =  new IButton("Information Network"); 
        BtnInfNet.addClickHandler(new ClickHandler() 
        {
       public void onClick(ClickEvent event) 
            {
                DynamicForm form = new DynamicForm();  
                form.setHeight100();  
                form.setWidth100();  
                form.setPadding(5);  
                form.setLayoutAlign(VerticalAlignment.CENTER);  
                

                form.setFields(txtSubnet,txtCC,txtName,txtBrand,txtNetMask,txtDmvpnIp,txtVlan100Ip,txtVlan101Ip);
                
                
                final Window  winModal = new Window();
                winModal.setWidth(440);  
                winModal.setHeight(300);
                winModal.setTitle("Information Network");  
                winModal.setShowMinimizeButton(true);
                winModal.setCanDragResize(true);
               
                //winModal.setIsModal(true);  
                //winModal.setShowModalMask(true);
                winModal.centerInPage();   
                winModal.addCloseClickHandler(new CloseClickHandler() 
                {
                public void onCloseClick(CloseClientEvent event) 
                    {
                        winModal.destroy();  
                    }
                });
                IButton BtnFind = new IButton("Find");
                BtnFind.addClickHandler(new ClickHandler()
                {

                    public void onClick(ClickEvent event) 
                    {
                        
                    }
                });
                IButton BtnNew = new IButton("New");
                BtnNew.addClickHandler(new ClickHandler()
                {
                    public void onClick(ClickEvent event) 
                    {
                       SC.say(txtSubnet.getValueAsString());
                    }
                });
                IButton BtnChange = new IButton("Change");
                BtnChange.addClickHandler(new  ClickHandler()
                {

                    public void onClick(ClickEvent event)
                    {
                        
                    }
                });
                IButton BtnDelete =  new IButton("Delete");
                BtnDelete.addClickHandler(new ClickHandler() 
                {
                    public void onClick(ClickEvent event)
                    {
                        
                    }
                });
                
                winModal.addItem(form);
                HLayout hzlayout = new HLayout(5); 
                hzlayout.addMember(BtnFind);
                hzlayout.addMember(BtnNew);
                hzlayout.addMember(BtnChange);
                hzlayout.addMember(BtnDelete);
               /* winModal.addItem(BtnFind);
                winModal.addItem(BtnNew);
                winModal.addItem(BtnChange);
                winModal.addItem(BtnDelete);*/
                winModal.addItem(hzlayout);
                 winModal.show(); 
            }
        });
        
        IButton printPreviewButton = new IButton("Print Preview");
        
        printPreviewButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                Canvas.showPrintPreview(printContainer);  
            }  
        });  
        
        printButtonLayout.addMember(BtnInfNet);
        printButtonLayout.addMember(printPreviewButton);  
        printContainer.addMember(printStack);  
        printContainer.addMember(printButtonLayout);  
  
  
       
        NetGrid.filterData(new Criteria("NetInfo","NetRes"), new DSCallback() {  
            public void execute(DSResponse response, Object rawData, DSRequest request) {  
                NetGrid.selectRecord(0);  
                printViewer.setData(new Record[]{NetGrid.getSelectedRecord()});
               
            }  
        });  
  
        printContainer.draw(); 
        
        
       
    }
    
}
