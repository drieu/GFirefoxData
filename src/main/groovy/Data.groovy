import fr.dr.gfirefoxdata.parser.JsonParser
import fr.dr.gfirefoxdata.parser.Sqlite

import java.sql.SQLException

/**
 * Created with IntelliJ IDEA.
 * User: drieu
 * Date: 09/05/14
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
class Data {

    public static void main(String[] args) {
        System.out.println("Working!");
        def MOZILLA_DIR = "C:\\Users\\USER\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles"

        new File(MOZILLA_DIR).eachDir {
            d ->
                println("Extract information from Directory :" + d.getPath())
                new File( d.getPath() ).eachFile() {
                    f->

                        Sqlite sqlite = new Sqlite(f);
                        if(f?.getName().startsWith("cookies.sqlite")) {
                            try {
                                sqlite.getCookies();
                            } catch(SQLException sqle) {
                                //
                            }
                        }

                        if(f?.getName().startsWith("addons.sqlite")) {
                            try {
                                sqlite.getAddons();
                            } catch(SQLException sqle) {
                                //
                            }
                        }

                        if(f?.getName().startsWith("formhistory.sqlite")) {
                            try {
                                sqlite.getHistory();
                            } catch(SQLException sqle) {
                                //
                            }
                        }
                        //content-prefs.sqlite
                        //healthreport.sqlite
                        //permissions.sqlite
                        //places.sqlite
                        //signons.sqlite
                        //webappsstore.sqlite


                        if(f?.getName().startsWith("search.json")) {
                            JsonParser jsonParser = new JsonParser();
                            jsonParser.getSearch(f.getPath())
                        }
                }
        }

    }
}
