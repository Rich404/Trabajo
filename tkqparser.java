/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yum.e3.server.development.apps.it.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author EMP2303
 */

// Originalmente public abstract class
public  class TkqParser{
    public final static String TAG_SEND = "SD ";
    public final static String TAG_COMMAND = "C ";
    public final static String TAG_RECEIVE = "RD ";
    public final static String TAG_LOCAL_COMMAND = "L ";
    public final static String TAG_FINAL = "\\|W\\|\n";
    
    public static int miTKQLength;
    private static Document moDOMData = new DocumentImpl();
    
    public final static ArrayList<LineParent> moParentList = new ArrayList();
    public final static ArrayList<TKQAtributes> moTKQList = new ArrayList();
    
    static String msTKQString = "";
    
    
    //protected abstract void parseFields(); Comentada ya que es  Parte de la clase abstracta
    
    //Hace un parseo de un archvio TKQ
    private static void parseAllSettings() {
        String[] lsTKQArray = msTKQString.split(TAG_FINAL);
        miTKQLength = lsTKQArray.length; 
        for(int i = 0; i < miTKQLength; i++){
            moTKQList.add(new TKQAtributes());
            if(lsTKQArray[i].contains(TAG_SEND)){ //Identifica si el comando es para enviar un archivo
                moTKQList.get(i).lsOptionId=""+(i+1);
                moTKQList.get(i).lsType="send";
                moTKQList.get(i).lsEliminate="cancel";
                moTKQList.get(i).lsSorter=""+(i+1);
                String[] lsLineArray = lsTKQArray[i].split("\\|");
                int j = 0;
                while(!lsLineArray[j+1].contains(TAG_SEND)){
                    moTKQList.get(i).lsPropList.add(lsLineArray[j]);
                    j++;
                }
                //Se encarga de la herencia
                if(!moParentList.isEmpty()){
                    if(moParentList.get(0).isLast())
                        moParentList.remove(0);
                    else{
                        moTKQList.get(i).lsParentId=moParentList.get(0).getParent()+"";
                        moParentList.get(0).decChild();
                    }
                }
                else
                    moTKQList.get(i).lsParentId="";
                if(!lsLineArray[j].contentEquals("0"))
                    moParentList.add(0, new LineParent(Integer.parseInt(moTKQList.get(i).lsOptionId),Integer.parseInt(lsLineArray[j])));
                String[] lsCommand = lsLineArray[j+1].split(" ");
                moTKQList.get(i).lsSource = lsCommand[1];
                moTKQList.get(i).lsDestination = lsCommand[2];
            }
            else if(lsTKQArray[i].contains(TAG_COMMAND)){ //Identifica si es un comando para ejecutar
                moTKQList.get(i).lsOptionId=""+(i+1);
                moTKQList.get(i).lsType="cmd";
                moTKQList.get(i).lsEliminate="cancel";
                moTKQList.get(i).lsSorter=""+(i+1);
                String[] lsLineArray = lsTKQArray[i].split("\\|");
                int j = 0;
                while(!lsLineArray[j+1].contains(TAG_COMMAND)){
                    moTKQList.get(i).lsPropList.add(lsLineArray[j]);
                    j++;
                }
                //Se encarga de la herencia
                if(!moParentList.isEmpty()){
                    if(moParentList.get(0).isLast())
                        moParentList.remove(0);
                    else{
                        moTKQList.get(i).lsParentId=moParentList.get(0).getParent()+"";
                        moParentList.get(0).decChild();
                    }
                }
                else
                    moTKQList.get(i).lsParentId="";
                if(!lsLineArray[j].contentEquals("0"))
                    moParentList.add(0, new LineParent(Integer.parseInt(moTKQList.get(i).lsOptionId),Integer.parseInt(lsLineArray[j])));
                String[] lsCommand = lsLineArray[j+1].split(TAG_COMMAND);
                moTKQList.get(i).lsSource = lsCommand[1];
                moTKQList.get(i).lsDestination = "";
            }
            else if(lsTKQArray[i].contains(TAG_RECEIVE)){ //Identifica si es un comando para trae un archivo
                moTKQList.get(i).lsOptionId=""+(i+1);
                moTKQList.get(i).lsType="receive";
                moTKQList.get(i).lsEliminate="cancel";
                moTKQList.get(i).lsSorter=""+(i+1);
                String[] lsLineArray = lsTKQArray[i].split("\\|");
                int j = 0;
                while(!lsLineArray[j+1].contains(TAG_RECEIVE)){
                    moTKQList.get(i).lsPropList.add(lsLineArray[j]);
                    j++;
                }
                //Se encarga de la herencia
                if(!moParentList.isEmpty()){
                    if(moParentList.get(0).isLast())
                        moParentList.remove(0);
                    else{
                        moTKQList.get(i).lsParentId=moParentList.get(0).getParent()+"";
                        moParentList.get(0).decChild();
                    }
                }
                else
                    moTKQList.get(i).lsParentId="";
                if(!lsLineArray[j].contentEquals("0"))
                    moParentList.add(0, new LineParent(Integer.parseInt(moTKQList.get(i).lsOptionId),Integer.parseInt(lsLineArray[j])));
                String[] lsCommand = lsLineArray[j+1].split(" ");
                moTKQList.get(i).lsSource = lsCommand[1];
                moTKQList.get(i).lsDestination = lsCommand[2];
            }
            else if (lsTKQArray[i].contains(TAG_LOCAL_COMMAND))// Cuando es comando Local "L"
            {
                
                
                moTKQList.get(i).lsOptionId=""+(i+1);
                moTKQList.get(i).lsType="local_cmd";
                moTKQList.get(i).lsEliminate="cancel";
                moTKQList.get(i).lsSorter=""+(i+1);
                String[] lsLineArray = lsTKQArray[i].split("\\|");
                int j = 0;
                while(!lsLineArray[j+1].contains(TAG_LOCAL_COMMAND)){
                    moTKQList.get(i).lsPropList.add(lsLineArray[j]);
                    j++;
                }
                //Se encarga de la herencia
                if(!moParentList.isEmpty()){
                    if(moParentList.get(0).isLast())
                        moParentList.remove(0);
                    else{
                        moTKQList.get(i).lsParentId=moParentList.get(0).getParent()+"";
                        moParentList.get(0).decChild();
                    }
                }
                else
                    moTKQList.get(i).lsParentId="";
                if(!lsLineArray[j].contentEquals("0"))
                    moParentList.add(0, new LineParent(Integer.parseInt(moTKQList.get(i).lsOptionId),Integer.parseInt(lsLineArray[j])));
                String[] lsCommand = lsLineArray[j+1].split(TAG_LOCAL_COMMAND);
                moTKQList.get(i).lsSource = lsCommand[1];
                moTKQList.get(i).lsDestination = "";
                
                
                
            }
        }
    }
    
    //Con base en la ArrayList de tasks de TKQ se crea un nuevo archivo en formato XML
    private static void createXMLDataFile(String lsXMLFilePath){
        Transformer transformer;
        Node loNode = moDOMData.createElement("tree_struct"); //Utiliza un metodo de la clase Document que crea un nodo con el nombre dado
        moDOMData.appendChild(loNode); //Liga el nodo a la raiz del Document 
        loNode = moDOMData.createElement("tree_options");
        moDOMData.getElementsByTagName("tree_struct").item(0).appendChild(loNode);
        for(int i = 0; i < miTKQLength; i++){
            Element loTreeOption = moDOMData.createElement("tree_option");
            loTreeOption.setAttribute("option_id",moTKQList.get(i).lsOptionId); //Crea un atributo para el elemento TreeOption con el nombre y contenido respectivamente
            loTreeOption.setAttribute("Tipo",moTKQList.get(i).lsType);
            loTreeOption.setAttribute("Eliminar",moTKQList.get(i).lsEliminate);
            loTreeOption.setAttribute("parent_id",moTKQList.get(i).lsParentId);
            loTreeOption.setAttribute("sorter",moTKQList.get(i).lsSorter);
            for(int j = 0; j < moTKQList.get(i).lsPropList.size(); j++)
                    loTreeOption.setAttribute("prop"+(j+1),moTKQList.get(i).lsPropList.get(j));
            Element loElement = moDOMData.createElement("Fuente");
            CDATASection loCDATA = moDOMData.createCDATASection(moTKQList.get(i).lsSource); //Crea una seccion de datos con el commando del TKQ
            loElement.appendChild(loCDATA);
            loTreeOption.appendChild(loElement);
            loElement = moDOMData.createElement("Destino");
            loCDATA = moDOMData.createCDATASection(moTKQList.get(i).lsDestination);
            loElement.appendChild(loCDATA);
            loTreeOption.appendChild(loElement);
            moDOMData.getElementsByTagName("tree_options").item(0).appendChild(loTreeOption);
        }
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(lsXMLFilePath));
            Source input = new DOMSource(moDOMData);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(input, output);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(TkqParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(TkqParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //Lee el archivo TKQ en la ruta lsTKQFile y lo extrae en corma de String en msTKQString
    private static void readTKQFile(String lsTKQFile) throws IOException {
        BufferedReader loBurreredReder = new BufferedReader(new FileReader(lsTKQFile));
        try {
            StringBuilder loStringBuilder = new StringBuilder();
            String lsLine = loBurreredReder.readLine();
            //Lee linea por linea y lo inserta en un StringBuilder
            while (lsLine != null) {
                loStringBuilder.append(lsLine);
                loStringBuilder.append("\n");
                lsLine = loBurreredReder.readLine();
            }
            msTKQString = loStringBuilder.toString();
        } finally {
            loBurreredReder.close();
        }
    }
    
    //Manejo de herencia 
    public static class LineParent{
        public int liParentId; //El ID de este padre para que los tasks hijos hagan referencia a el
        public int liHeritage; //El numero de hijos disponibles para ser asociados a un task
        public int liSons; //El numero establecido por el task padre que indica cuantos hijos tiene 
        LineParent(int Parent,int Heritage){
            liParentId = Parent;
            liHeritage = Heritage;
            liSons = Heritage;
        }
        //Para saber si es primer hijo
        public boolean isFirst(){
            return (liSons == liHeritage);
        }
        //Para saber si es el último hijo
        public boolean isLast(){
            return (liHeritage == 0);
        }
        //Cuando se ha asignado un hijo mas al task padre
        public void decChild(){
            liHeritage--;
        }
        //Obtener el ID del padre
        public int getParent(){
            return liParentId;
        }
    }
    
    //Estructura para guardar los atributos de los tasks del archivo TKQ
    public static class TKQAtributes{
        public String lsOptionId;
        public String lsType;
        public String lsEliminate;
        public String lsParentId;
        public String lsSorter;
        public String lsSource;
        public String lsDestination;
        public ArrayList<String> lsPropList;
        TKQAtributes(){
            lsPropList = new ArrayList();
        }
    }
    
    //Solo para pruebas
    public static void main(String[] args){
        try {
            readTKQFile("/home/richie/Poleo/tmp.tkq");
            parseAllSettings();
            createXMLDataFile("/home/richie/Poleo/tmp.xml");
        } catch (Exception poEx) {
            poEx.printStackTrace();
        }
    }
}

