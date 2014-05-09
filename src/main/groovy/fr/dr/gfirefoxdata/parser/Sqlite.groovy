package fr.dr.gfirefoxdata.parser;

import groovy.sql.Sql

/**
 * Class to extract informations from firefox fr.dr.gfox.parser.Sqlite files.
 * User: drieu
 * Date: 26/04/14
 */
class Sqlite {

    def url = ""

    Sqlite(File f) {
        if (f == null) {
            throw new IllegalArgumentException("File is null !")
        }
        url = "jdbc:sqlite:" + f.getPath()



    }

    /**
     * Get Navigation History.
     *
     * Result of .schema moz_formhistory :
     * CREATE TABLE moz_formhistory (id INTEGER PRIMARY KEY, fieldname TEXT NOT NULL, value TEXT NOT NULL, timesUsed INTEGER, firstUsed INTEGER, lastUsed INTEGER, guid TEXT);
     * CREATE INDEX moz_formhistory_index ON moz_formhistory(fieldname);
     * CREATE INDEX moz_formhistory_lastused_index ON moz_formhistory(lastUsed);
     * CREATE INDEX moz_formhistory_guid_index ON moz_formhistory(guid);
     */
    def getHistory() {

        println("url :" + url)
        def sql = Sql.newInstance(url, "org.sqlite.JDBC")

        sql.eachRow("select DISTINCT value from moz_formhistory") {
            println("value= ${it.value}")
        }
        println("")
    }


    /**
     * Get addons list.
     *
     * CREATE TABLE addon (internal_id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT UNIQUE, type TEXT, name TEXT, version TEXT, creator TEXT, creatorURL TEXT, description TEXT, fullDescription TEXT, developerComments TEXT, eula TEXT, iconURL TEXT, homepageURL TEXT, supportURL TEXT, contributionURL TEXT, contributionAmount TEXT, averageRating INTEGER, reviewCount INTEGER, reviewURL TEXT, totalDownloads INTEGER, weeklyDownloads INTEGER, dailyUsers INTEGER, sourceURI TEXT, repositoryStatus INTEGER, size INTEGER, updateDate INTEGER);
     * CREATE TRIGGER delete_addon AFTER DELETE ON addon BEGIN DELETE FROM developer WHERE addon_internal_id=old.internal_id; DELETE FROM screenshot WHERE addon_internal_id=old.internal_id; DELETE FROM compatibility_override WHERE addon_internal_id=old.internal_id; DELETE FROM icon WHERE addon_internal_id=old.internal_id; END;
     *
     */
    def getAddons() {

        println("url :" + url)
        def sql = Sql.newInstance(url, "org.sqlite.JDBC")

        sql.eachRow("select DISTINCT name from addon") {
            println("name= ${it.name}")
        }
        println("")
    }


    /**
     * Get a list of cookies web site.
     * Cookies are store in table moz_cookies of cookies.sqlite database.
     *
     * .schema moz_cookies :
     * CREATE TABLE moz_cookies (id INTEGER PRIMARY KEY, baseDomain TEXT, appId INTEGER DEFAULT 0, inBrowserElement INTEGER DEFAULT 0, name TEXT, value TEXT, host TEXT, path TEXT, expiry INTEGER, lastAccessed INTEGER, creationTime INTEGER, isSecure INTEGER, isHttpOnly INTEGER, CONSTRAINT moz_uniqueid UNIQUE (name, host, path, appId, inBrowserElement));
     * CREATE INDEX moz_basedomain ON moz_cookies (baseDomain, appId, inBrowserElement);
     */
    def getCookies() {

        println("url :" + url)
        def sql = Sql.newInstance(url, "org.sqlite.JDBC")

        sql.eachRow("select DISTINCT host from moz_cookies") {
            println("host= ${it.host}")
        }
        println("")
    }
}