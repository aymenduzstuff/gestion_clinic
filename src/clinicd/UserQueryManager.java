
package clinicd;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author aymen
 */
public class UserQueryManager {
    //is client in database 
    
    //
    public boolean isValidUser(DBconnection dBConnection, String name, String password) throws SQLException {

        boolean isValidUser = false;
        try (Connection con = dBConnection.getConnection()) {

            try (PreparedStatement stmt = con.prepareStatement("select count(*) from UserATable where name=? and password=?")) {

                try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {

                        int count = rs.getInt(1);
                        isValidUser = count > 0; // Maybe count == 1 would be more valid

                    }

                }

            }

        }

        return isValidUser;

    }
    
    public boolean checkPassword(DBconnection dBConnection, String name, String password) throws SQLException{
        Connection con = dBConnection.getConnection() ;
        try {
                String mysql_query = "SELECT password FROM clinicTest.admins WHERE username ='"+name+"';";
                
                PreparedStatement pst = con.prepareStatement(mysql_query);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    System.out.println("returned something");
                }else{
                    
                    System.out.println("returned nothing");
                }
                String correctPass;
                correctPass = rs.getString("password");
                System.out.println("the password retruned : "+correctPass);
                
                if(password.equals(correctPass)){
                    System.out.println("password correct");
                    pst.close();
                    return true ;
                    
                }else{
                    System.out.println("password or username incorrect");
                    
                    pst.close();
                    return false;
                }
                
            } catch (SQLException e) {
                
                System.err.println(e.getMessage());
            }
            return false;
        
    }
    public String getUsersCombo(DBconnection dBConnection) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String result = "";
        String sql = "SELECT * FROM admins ORDER BY ID  LIMIT 1";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                result += rs.getString("username");
                
            }
            return result ;
           
            
        }catch(SQLException ex){
            System.out.println(ex);
        }

        return "no result" ;
}

    void insertAdmin(DBconnection dBConnection,String id ,String username, String password) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "insert into admins values(?,?,?)"; 
        PreparedStatement ptst = con.prepareStatement(sql);
        ptst.setString(1 , id);
        ptst.setString(2 , username);
        ptst.setString(3 , password);
        ptst.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "new admin addes");
        con.close();
    }

    void insertClient(DBconnection dBConnection, String id, String nom, String prenom, String adress, String phone, Date date) throws SQLException {
        
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format1.format(date);
        
        
        Connection con =  dBConnection.getConnection() ;
        String sql = "INSERT INTO clinicTest.clients (id_client, nom, prenom, adresse, num_tel, date_naiss) VALUES (?, ?, ?, ?, ?, ?) " ;
        PreparedStatement ptst = con.prepareStatement(sql);
        ptst.setString(1 , id);
        ptst.setString(2 , nom);
        ptst.setString(3 , prenom);
        ptst.setString(4 , phone);
        ptst.setString(5 , adress);
        ptst.setString(6 , dateString);
        ptst.executeUpdate();
        
        System.out.println("new client added !");
        
        con.close();
    }
    void insertVisit(DBconnection dbc, String time, String client_id, String operation_id, String admin_id, String prix, String termine, String tooth) throws SQLException {
        
        
        
        
        Connection con =  dbc.getConnection() ;

        String sql = "INSERT INTO clinicTest.visits (seance_nbr, time, Client_id, Operation_id, Admin_id, prix, terminee, tooth) VALUES (1,?, ?, ?, ?, ?, ?, ?)" ;
        PreparedStatement ptst = con.prepareStatement(sql);
        
        ptst.setString(1 ,time );
        ptst.setString(2 ,client_id );
        ptst.setString(3 ,operation_id );
        ptst.setString(4 ,admin_id );
        ptst.setString(5 , prix);
        ptst.setString(6 , termine);
        ptst.setString(7 , tooth);
        
        ptst.executeUpdate();
        
        System.out.println("new visit added !");
        
        con.close();
    
    }
    void updateVisit(DBconnection dbc, String visit_id , String seance_nbr , String time , String admin_id, String prix, String termine) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        
        String mysql_query = "SELECT * FROM visits WHERE visit_id ='"+visit_id+"' and seance_nbr ='"+seance_nbr+"'";
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(mysql_query);
        rs.next();
        
        String client_id = rs.getString("Client_id");
        String operation_id =  rs.getString("Operation_id");
        String tooth =  rs.getString("tooth");
        System.out.println("client :"+client_id +" with tooth "+tooth);
        st.close();
        
        
                                                    
        String sql = "INSERT INTO clinicTest.visits (visit_id ,seance_nbr, time , Client_id , Operation_id , Admin_id, prix, terminee, tooth) VALUES (?,?,?, ?, ?, ?, ?, ?, ?)" ;
        PreparedStatement ptst = con.prepareStatement(sql);
        int seanceInt = Integer.parseInt(seance_nbr)+1 ;
        String seanceString = String.valueOf(seanceInt);
        
        ptst.setString(1 ,visit_id );
        ptst.setString(2 ,seanceString );
        ptst.setString(3 ,time );
        ptst.setString(4 ,client_id );
        ptst.setString(5 , operation_id);
        ptst.setString(6 , admin_id);
        ptst.setString(7 , prix);
        ptst.setString(8 , termine);
        ptst.setString(9 , tooth);
        
        ptst.executeUpdate();
        
        System.out.println(" visit updated !");
        
        con.close();
    
    }

    void insertOperation(DBconnection dBConnection, String id, String name, String price) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "insert into operations values(?,?,?)"; 
        PreparedStatement ptst = con.prepareStatement(sql);
        ptst.setString(1 , id);
        ptst.setString(2 , name);
        ptst.setString(3 , price);
        ptst.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "new operation added");
        con.close();
    }


    String[] getClientsResults(DBconnection dbc , String string) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "SELECT * FROM clients WHERE nom LIKE '%"+string+"%' OR prenom LIKE '%"+string+"%' ";
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(mysql_query);
                
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                
                while(rs.next() && i<k) {
                    result[i] = rs.getString("id_client")+":::"+rs.getString("nom")+":::"+rs.getString("prenom");
                    i++;
                }  
                st.close();
            } catch (SQLException e) {
                System.out.println("error in searchUsersCatalogueResult...");
            }
        return result;
    }

    void updateCombo(JComboBox combo, DBconnection dBConnection , int i) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "";
        String col = "" ;
        if(i == 1){
            
            sql = "select * from admins";
            col = "username" ;
        }else if(i == 2){
            sql = "select * from operations";
            col = "nom_operation" ;
        }else if(i == 3){
            for(int j = 0 ; j < 32 ;j++){
                combo.addItem(j);
            }
        }
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getString(col));
                
            }
           
            
        }catch(Exception ex){
            
        }
        con.close();
    }

    String[] getVisitsResults(DBconnection dbc, String code) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String newquery = "select visit_id, max(seance_nbr) as dern_seance, max(time) as dern_date, client_id,  nom_operation , admin_id , sum(prix) as total, tooth, max(terminee) as statu  from visits  inner join operations on operations.id_operation = visits.operation_id where client_id='"+code+"' group by visit_id " ;

        String mysql_query = "select * from visits where client_id='"+code+"'  ";
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(newquery);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(newquery);
                
                int numberOfUsers = 0;
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                System.out.println("there are this many rows : "+ k);
                while(rs.next() && i<k) {
                    numberOfUsers++;
                    //result[i] = rs.getString("visit_id")+":::"+rs.getString("time")+":::"+rs.getString("Operation_id")+":::"+rs.getString("tooth")+":::"+rs.getString("seance_nbr")+":::"+rs.getString("prix")+":::"+rs.getString("terminee");
                    result[i] = rs.getString("visit_id")+":::"+rs.getString("tooth")+":::"+rs.getString("nom_operation")+":::"+rs.getString("dern_seance")+":::"+rs.getString("dern_date")+":::"+rs.getString("total")+":::"+rs.getString("statu");
                    
                    System.out.println("\n");
                    System.out.println("content : "+result[i]);
                    i++;
                    
                }  
                
                st.close();
            } catch (SQLException e) {
                System.out.println("error when retrieving visit data...");
            }
        return result;
    }

    Object getOperationIdByName(DBconnection dbc, String operation) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String sql = "select id_operation from operations where nom_operation='"+operation+"'";
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            System.out.println("returned from if");
            return rs.getString(1);
        }
        
        
        con.close();
        System.out.println("returned from function body");
        return rs.getString("id_operation");
        }
}

