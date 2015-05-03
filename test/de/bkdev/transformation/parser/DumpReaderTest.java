package de.bkdev.transformation.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DumpReaderTest {

	@Test
	public void testeStringEinlesen(){
		String temp = "DROP TABLE IF EXISTS `test`; CREATE TABLE `test` ( `id` int(11) NOT NULL auto_increment, `time` timestamp NOT NULL default CURRENT_TIMESTAMP,  PRIMARY KEY  (`id`)) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1; SET character_set_client = @saved_cs_client;";
		
	}

}
