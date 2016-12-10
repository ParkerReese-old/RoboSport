package models;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import interpreter.Script;
import models.Robot.Type;

/**
 * The RobotLibrarySocket is the only entity that communicates with the RobotLibrary
 * All data needed from the RobotLibrary is sent as a request through the RobotLibrarySocket
 * The RobotLibrarySocket can only communicate with the RobotLibrary on UofS_Secure network
 * @author Parker
 *
 */
public class RobotLibrarySocket {

    protected static final Script DEFAULT_SCRIPT = new Script(
            "variable moved ; : moved? moved ? ; moved false ! : firstMove moved? if else move! move! move! moved true ! then ; variable shot ; : canShoot? shot ? ; : shoot!! canShoot? if drop drop else shoot! shot true ! then then ; : doNotShoot drop drop ; : enemy? team <> ; : nonZeroRange? dup 0 <> ; : tryShooting! enemy? swap nonZeroRange? rot and if shoot!! else doNotShoot then drop ; : shootEveryone scan! 1 - dup 0 < if else 0 do 1 identify! tryShooting! loop then ; : play firstMove shot false ! shootEveryone ; ");
    protected static final Robot DEFAULT_TANK =
            new Robot(Robot.Type.TANK, "DefaultTank", DEFAULT_SCRIPT);
    protected static final Robot DEFAULT_SNIPER =
            new Robot(Robot.Type.SNIPER, "DefaultSniper", DEFAULT_SCRIPT);
    protected static final Robot DEFAULT_SCOUT =
            new Robot(Robot.Type.SCOUT, "DefaultScout", DEFAULT_SCRIPT);


    /**
     * Sends a JSON formatted String to the RobotLibrary to update statistics on the all the Robots after a game
     * @param gameboard the board game, which holds Statistics (the immediate stats) that need to be send to RobotLibrary
     */
    @SuppressWarnings("unchecked")
    public static void updateStatistics(GameBoard gameboard){

        if(gameboard == null){
            throw new RuntimeException("gamboard given to updateStatistics was null");
        }
        /* calls to the GameBoard's Stistics map will be called often */
        Map<String, Map<String,Integer>> stats = new HashMap<String,Map<String,Integer>>();
        stats = gameboard.getImmediateStatistics();

        JSONObject innerpeice = new JSONObject();
        JSONObject whole = new JSONObject();

        String hostname = "gpu0.usask.ca";
        int port = 20001;

        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream

        Socket clientSocket = null;  
        DataOutputStream os = null;
        BufferedReader is = null;

        /* needs to update statistic for each robot that played */
        for(Robot r : gameboard.getAllRobots()){
            String key1 = r.getRobotStringID(); 
            innerpeice.put("team", "A2");
            innerpeice.put("name", key1);
            innerpeice.put("class", r.getType());
            innerpeice.put("wins", stats.get(key1).get("wins"));
            innerpeice.put("losses", stats.get(key1).get("losses"));
            innerpeice.put("lived", stats.get(key1).get("lived"));
            innerpeice.put("died", stats.get(key1).get("died"));
            innerpeice.put("absorbed", stats.get(key1).get("absorbed"));
            innerpeice.put("killed", stats.get(key1).get("killed"));
            innerpeice.put("moved", stats.get(key1).get("distance"));

            whole.put("update", innerpeice);

            /* Initialization section:
               Try to open a socket on the given port
               Try to open input and output streams */

            try {
                clientSocket = new Socket(hostname, port);
                os = new DataOutputStream(clientSocket.getOutputStream());
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host: " + hostname);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to: " + hostname);
            }

            /* If everything has been initialized then we want to write some data
               to the socket we have opened a connection to on the given port */

            if (clientSocket == null || os == null || is == null) {
                System.err.println( "Something is wrong. One variable is null." );
                return;
            }

            try {                              
                /* convert our JSON object, which holds the properly formatted request, to a string */
                String Input = whole.toJSONString();
                System.out.println(Input);
                /* send Input to the Robot Library */
                os.writeBytes( Input + "\n" );    

                /* recieve what the Robot Library sent back */
                /* Uncoment these 3 lines to print response from RL to console 
                 * String responseLine = is.readLine();
                 * System.out.println(responseLine); 
                 * */

                /* clean up:
                   close the output stream
                   close the input stream
                   close the socket */

                os.close();
                is.close();
                clientSocket.close();              

            } 
            catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
        /*after loop is done, simply return, stats will be updated */
        return;
    }    

    /**
     * Sends a JSON formated String to RobotLibrary with a request for all the Robots in Library of the team A2
     * @return a string holding all the Robots data. (name, type, wins, losses, etc)
     */
    @SuppressWarnings("unchecked")
    public static String enumerateRobotsAndStats(){
        /* setup proper format of request */
        JSONObject innerpeice = new JSONObject();
        innerpeice.put("data" , "full");
        innerpeice.put("team", "A2");

        JSONObject whole = new JSONObject();
        whole.put("list-request", innerpeice);

        String hostname = "gpu0.usask.ca";
        int port = 20001;

        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream

        Socket clientSocket = null;  
        DataOutputStream os = null;
        BufferedReader is = null;

        /* Initialization section:
               Try to open a socket on the given port
               Try to open input and output streams */

        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        /* If everything has been initialized then we want to write some data
               to the socket we have opened a connection to on the given port */

        if (clientSocket == null || os == null || is == null) {
            System.err.println( "Something is wrong. One variable is null." );
            return null;
        }

        try {                              

            String Input = whole.toString();

            /* send Input to the Robot Library */
            os.writeBytes( Input + "\n" );    
            /* recieve what the Robot Library sent back */
            String responseLine = is.readLine();            
            //System.out.println(responseLine);
            /* clean up:
                   close the output stream
                   close the input stream
                   close the socket */

            os.close();
            is.close();
            clientSocket.close();  
            /*return what the Robot Library sent back given the request */
            return responseLine;

        } 
        catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

        /* null will only get returned if the code exits the try{} statement from an error */
        return null;
    }


    /**
     * Sends a JSON formated String to RobotLibrary with a request for all the Robots in Library of the team A2
     * @return a string holding all the Robots data. (name, type, wins, losses, etc)
     */
    @SuppressWarnings("unchecked")
    public static String enumerateRobotsAndStatsBrief(){
        /* setup proper format of request */
        JSONObject innerpeice = new JSONObject();
        innerpeice.put("data" , "brief");
        innerpeice.put("team", "A2");

        JSONObject whole = new JSONObject();
        whole.put("list-request", innerpeice);

        String hostname = "gpu0.usask.ca";
        int port = 20001;

        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream

        Socket clientSocket = null;  
        DataOutputStream os = null;
        BufferedReader is = null;

        /* Initialization section:
               Try to open a socket on the given port
               Try to open input and output streams */

        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        /* If everything has been initialized then we want to write some data
               to the socket we have opened a connection to on the given port */

        if (clientSocket == null || os == null || is == null) {
            System.err.println( "Something is wrong. One variable is null." );
            return null;
        }

        try {                              

            String Input = whole.toString();

            /* send Input to the Robot Library */
            os.writeBytes( Input + "\n" );    
            /* recieve what the Robot Library sent back */
            String responseLine = is.readLine();            
            //System.out.println(responseLine);
            /* clean up:
                   close the output stream
                   close the input stream
                   close the socket */

            os.close();
            is.close();
            clientSocket.close();  
            /*return what the Robot Library sent back given the request */
            return responseLine;

        } 
        catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

        /* null will only get returned if the code exits the try{} statement from an error */
        return null;
    }

    /**
     * This method will give you all robots in the Robot Library as a Robot object
     * @return a List of all the Robots (as a Robot object) 
     */
    public static List<Robot> getAllRobotsSlow(){
        /* get all the robot names */
        List<String> robotNames = enumerateRobotNames();
        List<Robot> robots = new LinkedList<Robot>();
        /* for each robot name, get the robot object */
        for(String s : robotNames){
            robots.add(retrieveRobotFromLibrary(s));   
        }

        return robots;
    }

    @SuppressWarnings("unchecked")
    /**
     * method to take all the robots in our Robot Library and make them into robot objects
     * @return list of robot objects that are in the RL
     */
    public static List<Robot> getAllRobotsFast(){
        /* setup proper format of request */
        JSONObject innerpeice = new JSONObject();
        innerpeice.put("data" , "full");
       // innerpeice.put("team", "A2");

        JSONObject whole = new JSONObject();
        whole.put("list-request", innerpeice);

        String hostname = "gpu0.usask.ca";
        int port = 20001;

        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream

        Socket clientSocket = null;  
        DataOutputStream os = null;
        BufferedReader is = null;

        /* Initialization section:
               Try to open a socket on the given port
               Try to open input and output streams */

        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        /* If everything has been initialized then we want to write some data
               to the socket we have opened a connection to on the given port */

        if (clientSocket == null || os == null || is == null) {
            System.err.println("Something is wrong. One variable is null." );
            List<Robot> defaultRobots = new ArrayList<Robot>(3);
            defaultRobots.add(DEFAULT_TANK);
            defaultRobots.add(DEFAULT_SNIPER);
            defaultRobots.add(DEFAULT_SCOUT);
            return defaultRobots;
        }

        try {                              

            String Input = whole.toString();

            /* send Input to the Robot Library */
            os.writeBytes( Input + "\n" );               

            JSONArray JA = new JSONArray();
            JSONParser parser = new JSONParser();

            try {
                /* recieve what the Robot Library sent back */
                JA = ( JSONArray ) parser.parse(is.readLine());
            } catch (org.json.simple.parser.ParseException e) {                
                e.printStackTrace();
            }

            /* clean up:
                   close the output stream
                   close the input stream
                   close the socket */

            os.close();
            is.close();
            clientSocket.close();                     

            JSONObject jo = new JSONObject();
            JSONObject jo2 = new JSONObject();
            List<Robot> robotList = new LinkedList<Robot>();

            /* turn the array of JSON objects into Robots
             * Must loop for each index of JA (the JSON Array) */
            for(int i =0; i <JA.size(); i++){
                try {
                    /* due to what the RobotLibrary returns, must parse through two JSON Objects
                     * to get to what we are wanting */
                    jo = (JSONObject) parser.parse(JA.get(i).toString());
                    jo2 = (JSONObject) parser.parse(jo.get("script").toString());

                } catch (org.json.simple.parser.ParseException e) {                 
                    e.printStackTrace();
                }
                /* peice out the TYPE for this Robot */
                Type type =  Type.valueOf(jo2.get("class").toString().toUpperCase());  
                
                /* peice out the script for this Robot */
                String scriptString = "";             
                JSONArray scriptChunks = new JSONArray();
                
                scriptChunks = (JSONArray) jo2.get("code");
                
               for(int j = 0; j < scriptChunks.size(); j++){
                   scriptString += scriptChunks.get(j).toString().trim() + " ";  
                   System.out.print(scriptChunks.get(j).toString().trim() + " ");
               }
                /* create a script using the peiced together scriptString */
                Script script = new Script(scriptString);

                /* create the Robot */
                robotList.add(new Robot(type, jo2.get("name").toString(), script));

            }           

            return robotList;

        } 
        catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

        /* null will only get returned if the code exits the try{} statement from an error */
        return null;
    }

    /**
     * returns a LinkedList of all the names of Robots we have in our Robot Library (Robots of team A2)
     * @return a linkedList of every Robot name we have in the RobotLibrary
     */
    @SuppressWarnings("unchecked")
    public static List<String> enumerateRobotNames(){

        List<String> robotNames = new LinkedList<String>();

        /* setup of request to Robot Library */
        JSONObject innerpeice = new JSONObject();
        innerpeice.put("data" , "full");
        //innerpeice.put("team", "A2");

        JSONObject whole = new JSONObject();
        whole.put("list-request", innerpeice);

        String hostname = "gpu0.usask.ca";
        int port = 20001;

        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream

        Socket clientSocket = null;  
        DataOutputStream os = null;
        BufferedReader is = null;

        /* Initialization section:
               Try to open a socket on the given port
               Try to open input and output streams */

        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        /* If everything has been initialized then we want to write some data
               to the socket we have opened a connection to on the given port */

        if (clientSocket == null || os == null || is == null) {
            System.err.println( "Something is wrong. One variable is null." );
            return null;
        }

        try {                              

            String Input = whole.toString();

            /* send Input to the Robot Library */
            os.writeBytes( Input + "\n" );    
            /* recieve what the Robot Library sent back */
            String responseLine = is.readLine();

            /* clean up:
            close the output stream
            close the input stream
            close the socket */

            os.close();
            is.close();
            clientSocket.close();  

            /* break up the returned string into the names of the Robot's
             * adding it to the List of robotNames each time */
            while(responseLine.indexOf("name") != -1){
                responseLine = responseLine.substring(responseLine.indexOf("name"));
                responseLine = responseLine.substring(responseLine.indexOf("\"") + 3);
                robotNames.add(responseLine.substring(0, responseLine.indexOf("\"")));
            }          
        } 
        catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

        return robotNames;
    }

    /**
     * Sends a JSON formated String to the RobotLibrary with a request for all information on a specific Robot
     * that information will the be peiced out and a Robot object will be created from it
     * @param RobotStringID the Robots name for which you want a Robot object for
     * @return a Robot object, holding all of the appropriate information for the Robot's name that was provided
     */
    @SuppressWarnings("unchecked")
    public static Robot retrieveRobotFromLibrary(String robotStringID){
        /* setup for request that will be sent to Robot Library */
        JSONObject innerpeice = new JSONObject();
        innerpeice.put("data", "full");
        innerpeice.put("name", robotStringID);
        innerpeice.put("team", "A2");

        JSONObject whole = new JSONObject();
        whole.put("list-request", innerpeice);

        String hostname = "gpu0.usask.ca";
        int port = 20001;

        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream

        Socket clientSocket = null;  
        DataOutputStream os = null;
        BufferedReader is = null;

        /* Initialization section:
           Try to open a socket on the given port
           Try to open input and output streams */

        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        /* If everything has been initialized then we want to write some data
           to the socket we have opened a connection to on the given port */

        if (clientSocket == null || os == null || is == null) {
            System.err.println( "Something is wrong. One variable is null." );
            return null;
        }

        try {                              
            String Input = whole.toJSONString();
            System.out.println(Input);
            /* send Input to the Robot Library */
            os.writeBytes( Input + "\n" );    
            /* recieve what the Robot Library sent back */
            String responseLine = is.readLine();

            /* clean up:
            close the output stream
            close the input stream
            close the socket */

            os.close();
            is.close();
            clientSocket.close();  

            /* peice out what class the Robot has from the string */
            responseLine = responseLine.substring(responseLine.indexOf("class"));
            responseLine = responseLine.substring(responseLine.indexOf("\"") + 3);

            String Rclass = (responseLine.substring(0, responseLine.indexOf("\"")));
            Rclass = Rclass.toUpperCase();

            /*peice out the String of forth code that is the script of the Robot */
            responseLine = responseLine.substring(responseLine.indexOf("code"));
            responseLine = responseLine.substring(responseLine.indexOf("\"") + 3);
            String code = (responseLine.substring(1, (responseLine.length() - 5)));
            Script script = new Script(code);                        

            /* convert the string version of the Robot's type into an actual type */
            Type type =  Type.valueOf(Rclass);            

            Robot r = new Robot(type, robotStringID, script);            

            /*return the created Robot */
            return r;

        } 
        catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

        /* this will only get returned if the code exits the try{} statement from an error */
        return null;
    }

    /**
     * Given a Robot (must be a Npc Robot), creates a JSON file to be sent to RobotLibrary to register the robot
     * THIS METHOD DOES NOT WORK AND IS NOT REQUIRED TO WORK. USERS CAN NOT REGISTER  
     * TO REGISTER A ROBOT ASK PARKER TO HARD CODE IN A REQUEST
     * @param robot the Robot you want to register
     */

    @SuppressWarnings({"unchecked", "unused"})
    private static void registerRobot(Robot robot){
        JSONObject innerpeice = new JSONObject();

        innerpeice.put("name", "Creeper_good_Scout");
        innerpeice.put("team", "A2");
        innerpeice.put("class", "Scout");
        List<String> test = new LinkedList<String>();
        //test.add(" \"variable moved ; ( have I moved? )         \"                          , \": moved? moved ? ;                         \"              , \"moved false !                              \"              , \"                                           \"              , \": firstMove ( move to center first )       \"              , \"       moved? if                           \"              , \"                ( already moved )          \"              , \"              else                         \"              , \"                move move move             \"              , \"                moved true !               \"              , \"              then ;                       \"              , \"                                           \"              , \"variable shot  ; ( have I shot this play? )\"                          , \": canShoot? ( -- b ) ( shot available? )   \"              , \"            shot? ;                       \"              , \"                                           \"              , \": shoot!! ( id ir -- ) ( shoot if allowed )\"              , \"          canShoot? if                     \"              , \"             pop pop      ( remove ir id ) \"              , \"          else                             \"              , \"              shoot!      ( really shoot ) \"              , \"              shot true ! ( remember it )  \"              , \"            then                           \"              , \"          then ;                           \"              , \"                                           \"              , \": doNotShoot ( id ir -- ) ( pretend shot ) \"              , \"             pop pop ;                     \"              , \"                                           \"              , \": enemy? ( s -- b ) ( decide if enemy )    \"              , \"         team <> ;                         \"              , \"                                           \"              , \": nonZeroRange? ( i -- b i )               \"              , \"             dup 0 <> ;                    \"              , \"                                           \"              , \": tryShooting! ( ih id ir st -- )          \"              , \"               enemy?                      \"              , \"               swap nonZeroRange? rot      \"              , \"             and if                        \"                          , \"                   shoot!!                 \"                          , \"                 else                      \"                          , \"                   doNotShoot              \"                          , \"                 then pop ( remove ih ) ;  \"                          , \"                                           \"              , \": shootEveryone ( try shot at all targets )\"              , \"       scan! 1 - dup 0 <                   \"                          , \"       if                                  \", \"         ( no one to shoot at )            \"                          , \"       else 0 do                           \", \"                I identify! tryShooting!   \"              , \"              loop                         \"              , \"       then ;                              \"                          , \"                                           \"              , \": play ( -- )                              \"              , \"       firstMove                           \"             , \"       shot false ! ( prepare to shoot )   \"              , \"       shootEveryone ;                     \" " );
        //test.add(" \"variable moved ; ( have I moved? )         \"                , \": moved? moved ? ;                         \"                , \"moved false !                              \"                , \"                                           \"                , \": firstMove ( align along left edge )      \"                , \"       moved? if                           \"                , \"                ( already moved )          \"                , \"              else                         \"                , \"                5 turn!                    \"                , \"              moved true !                        \"               , \"                   then ;                        \"                , \"              \"                  , \" : edgeMove ( -- ) ( move along an edge )   \"                , \"           0 check!                        \"                , \"           .\\\"OUT OF BOUNDS\\\" =            \" , \"           if                              \"                , \"             1 turn!                       \"                , \"           else                            \"                , \"             ( )                           \"                , \"           then move! ;                    \"                , \"                                           \"                , \": noMovesLeft? ( -- b ) ( no moves left? ) \"                , \"             movesLeft 0 <> ;              \"                , \"                                           \"                , \"variable shot ; ( have I shot this play? ) \"                , \": canShoot? ( -- b ) ( shot available? )   \"                , \"            shot ? ;                        \"                , \"                                           \"                , \": shoot!! ( id ir -- ) ( shoot if allowed )\"                , \"          canShoot? if                     \"                , \"             pop pop      ( remove ir id ) \"                , \"          else                             \"                , \"              shoot!      ( really shoot ) \"                , \"              shot true ! ( remember it )  \"                , \"            then ;                          \"                , \"                                     \"                , \" : doNotShoot ( id ir -- ) ( pretend shot ) \"                , \"             pop pop ;                     \"                , \"                                           \"                , \": enemy? ( s -- b ) ( decide if enemy )    \"                , \"         team <> ;                         \"                , \"                                           \"                , \": nonZeroRange? ( i -- b i )               \"                , \"             dup 0 <> ;                    \"                , \"                                           \"                , \": tryShooting! ( ih id ir st -- )          \"              , \"             enemy?                        \"              , \"             swap nonZeroRange? rot        \"              , \"             and if                        \"                          , \"                   shoot!!                 \"                          , \"                 else                      \"                          , \"                   doNotShoot              \"                          , \"                 then pop ( remove ih ) ;  \"                          , \"                                           \"              , \": shootEveryone ( try shot at all targets )\"              , \"       scan!                               \"              , \"       1 -                                 \"              , \"       dup 0 < if                          \"              , \"         ( no one to shoot at )            \"                          , \"       else 0 do                           \"              , \"                I identify! tryShooting!   \"              , \"              loop                         \"              , \"       then ;                              \"                          , \"                                           \"              , \": play ( -- )                              \"              , \"       firstMove                           \"              , \"       shot false ! ( prepare to shoot )   \"                          , \"       begin                               \"                          , \"         edgeMove                          \"              , \"         shootEveryone                     \"              , \"       noMovesLeft? until ;                \"               ");
        //test.add(" \": play ( -- ) 5 0 do I 1 empty? if .\"no one there\" \" , \" else I 1 shoot! leave \" ,\" then \" , \" loop ; \" ");
        
        
        innerpeice.put("code", test);

        JSONObject scriptPeice = new JSONObject();
        scriptPeice.put("script", innerpeice);

        JSONObject whole = new JSONObject();
        whole.put("register", scriptPeice);

        String hostname = "gpu0.usask.ca";
        int port = 20001;

        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream

        Socket clientSocket = null;  
        DataOutputStream os = null;

        BufferedReader is = null;

        /* Initialization section:
           Try to open a socket on the given port
           Try to open input and output streams */

        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        /* If everything has been initialized then we want to write some data
           to the socket we have opened a connection to on the given port */

        if (clientSocket == null || os == null || is == null) {
            System.err.println( "Something is wrong. One variable is null." );
            return;
        }

        try {                              

            //String Input = whole.toString();           

            String Input = "{ \"register\" :     { \"team\"    : \"A2\"    , \"class\"   : \"Scout\"    , \"name\"    :  \"sit_and_shoot\"    , \"code\"    : " + test +"    }}";

            System.out.println(Input);
            /* send Input to the Robot Library */
            //Commenting out the output so as to not accidentally register a robot
            os.writeBytes( Input + "\n" );    
            /* recieve what the Robot Library sent back */
            String responseLine = is.readLine();
            System.out.println(responseLine);
            /* clean up:
               close the output stream
               close the input stream
               close the socket */

            os.close();
            is.close();
            clientSocket.close();  
            /*return what the Robot Library sent back given the request */
            //System.out.println(responseLine);

        } 
        catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

        /* this will only get returned if the code exits the try{} statement which returns the wanted string */
        return;
    }



    /**
     * Will send a JSON file to RobotLibrary to delete a certain Robot from it's database
     * THIS METHOD DOES NOT WORK AND IS NOT REQUIRED TO WORK. USERS CAN NOT DELETE
     * TO DELETE A ROBOT ASK PARKER TO HARD CODE IN A REQUEST
     * @param robot the robot you wish to remove
     */
    @SuppressWarnings("unchecked")
    public static void deleteRobot(Robot robot){
        JSONObject innerpeice = new JSONObject();

        innerpeice.put("name", "deathClaw3");
        innerpeice.put("team", "A2");

        JSONObject whole = new JSONObject();
        whole.put("delete", innerpeice);

        String hostname = "gpu0.usask.ca";
        int port = 20001;

        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream

        Socket clientSocket = null;  
        DataOutputStream os = null;
        BufferedReader is = null;

        /* Initialization section:
           Try to open a socket on the given port
           Try to open input and output streams */

        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        /* If everything has been initialized then we want to write some data
           to the socket we have opened a connection to on the given port */

        if (clientSocket == null || os == null || is == null) {
            System.err.println( "Something is wrong. One variable is null." );
            return;
        }

        try {                              

            //String Input = "{ \"delete\" : { \"team\" : \"A2\" , \"name\" : \"deathClaw\" } }";
            String Input = whole.toString();
            System.out.println(Input);    
            /* send Input to the Robot Library */
            os.writeBytes(Input);
            //  System.out.println(whole.toString());
            /* recieve what the Robot Library sent back */
            String responseLine = is.readLine();
            System.out.println(responseLine);
            /* clean up:
               close the output stream
               close the input stream
               close the socket */

            os.close();
            is.close();
            clientSocket.close();  
            /*return what the Robot Library sent back given the request */
            //System.out.println(responseLine);

        } 
        catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

        /* this will only get returned if the code exits the try{} statement which returns the wanted string */
        return;
    }



    /** main method used for testing purposes */
    public static void main(String args[]){
        /** TESTING BEGINS */
        Robot scout = new Robot(Robot.Type.SCOUT, "terminator1"); 
        Robot sniper = new Robot(Robot.Type.SNIPER, "snoopy1");
        Robot tank = new Robot(Robot.Type.TANK, "deathClaw1");
        scout.setLocation(new BoardPosition(4,4, 0));
        sniper.setLocation(new BoardPosition(4,4, 0));
        tank.setLocation(new BoardPosition(4,4, 0));
        /** adding robots to a list */
        LinkedList<Robot> theList = new LinkedList<Robot>();
        theList.add(scout);
        theList.add(sniper);
        theList.add(tank);
        /** creating a team */
        Team team1 = new Team(theList, Team.TeamColor.RED, 1);

        Robot scout2 = new Robot(Robot.Type.SCOUT, "terminator2"); 
        Robot sniper2 = new Robot(Robot.Type.SNIPER, "snoopy2");
        Robot tank2 = new Robot(Robot.Type.TANK, "deathClaw2");
        scout2.setLocation(new BoardPosition(4,4, 0));
        sniper2.setLocation(new BoardPosition(4,4, 0));
        tank2.setLocation(new BoardPosition(4,4, 0));
        /** adding robots to a list */
        LinkedList<Robot> theList2 = new LinkedList<Robot>();
        theList.add(scout2);
        theList.add(sniper2);
        theList.add(tank2);
        /** creating a team */
        Team team2 = new Team(theList2, Team.TeamColor.RED, 2);

        Robot scout3 = new Robot(Robot.Type.SCOUT, "terminator3"); 
        Robot sniper3 = new Robot(Robot.Type.SNIPER, "snoopy3");
        Robot tank3 = new Robot(Robot.Type.TANK, "deathClaw3");
        scout3.setLocation(new BoardPosition(4,4, 0));
        sniper3.setLocation(new BoardPosition(4,4, 0));
        tank3.setLocation(new BoardPosition(4,4, 0));
        /** adding robots to a list */
        LinkedList<Robot> theList3 = new LinkedList<Robot>();
        theList3.add(scout3);
        theList3.add(sniper3);
        theList3.add(tank3);
        /** creating a team */
        Team team3 = new Team(theList3, Team.TeamColor.RED, 3);

        LinkedList<Team> teamList = new LinkedList<Team>();
        teamList.add(team1);
        teamList.add(team2);
        teamList.add(team3);

        GameBoard gBoard = new GameBoard(teamList);

        gBoard.updateImmediateStatistics("terminator1", "absorbed", 2);
        gBoard.updateImmediateStatistics("terminator2", "died", 1);

        gBoard.updateImmediateStatistics("snoopy1", "wins", 1);
        gBoard.updateImmediateStatistics("snoopy1", "wins", 2);

        gBoard.updateImmediateStatistics("deathClaw1", "losses", 4);
        gBoard.updateImmediateStatistics("deathClaw3", "distance", 24);

        /* remove system.out comments in methods to visualize working JSON file output's (one for each robot) */
        /* testing of RobotLibrarySocket will now simply be done by System.out.println on the returned file
         * from the Robot Library socket as well as what is returned when enumerating the RobotLibrary
         */
        

        //List<Robot> robotList = new LinkedList<Robot>();
        //robotList = getAllRobotsFast();
        //System.out.println(robotList.get(0).getScript());
        //retrieveRobotFromLibrary("Creeper");        
        //enumerateRobotsAndStats();
        //System.out.println(enumerateRobotNames().toString());
        //deleteRobot(tank3);

        //registerRobot(tank3);
        /** TESTING ENDS */
    }
}
