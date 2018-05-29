INSERT INTO `lod`.`link` (`uri1`, `uri2`) VALUES ('http://dbpedia.org/class/yago/LivingThing100004258','http://dbpedia.org/resource/Kim_Heechul')  ON DUPLICATE KEY UPDATE `uri1` = `uri1` , `uri2` = `uri2`;


select uri2 from `lod`.`link` as b where b.uri1 = 'http://dbpedia.org/resource/Su_Veneno'   and (  b.uri2 = 'http://dbpedia.org/resource/The_Beatles' OR   b.uri2 = 'http://dbpedia.org/resource/Drake_(rapper)' OR   b.uri2 = 'http://dbpedia.org/resource/Michael_Jackson' OR   b.uri2 = 'http://dbpedia.org/resource/Erykah_Badu' OR   b.uri2 = 'http://dbpedia.org/resource/Ludacris' OR   b.uri2 = 'http://dbpedia.org/resource/The_Hangover' OR   b.uri2 = 'http://dbpedia.org/resource/Monica_(singer)' OR   b.uri2 = 'http://dbpedia.org/resource/Sam_Sparro' OR   b.uri2 = 'http://dbpedia.org/resource/Rihanna' OR   b.uri2 = 'http://dbpedia.org/resource/The_Temptations_(TV_miniseries)' OR   b.uri2 = 'http://dbpedia.org/resource/Fatal_Attraction' OR   b.uri2 = 'http://dbpedia.org/resource/Basic_Instinct' OR   b.uri2 = 'http://dbpedia.org/resource/Because_I_Said_So_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Baby_Mama_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Titanic_(1997_film)' OR   b.uri2 = 'http://dbpedia.org/resource/Maxwell_(musician)' OR   b.uri2 = 'http://dbpedia.org/resource/Jill_Scott' OR   b.uri2 = 'http://dbpedia.org/resource/Due_Date' OR   b.uri2 = 'http://dbpedia.org/resource/John_Mayer' OR   b.uri2 = 'http://dbpedia.org/resource/Pocahontas_(1995_film)' OR   b.uri2 = 'http://dbpedia.org/resource/He\'s_Just_Not_That_Into_You_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Anthony_Hamilton_(musician)' OR   b.uri2 = 'http://dbpedia.org/resource/Kate_Voegele' OR   b.uri2 = 'http://dbpedia.org/resource/Revolutionary_Road_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Sam_Cooke' OR   b.uri2 = 'http://dbpedia.org/resource/Lady_Gaga' OR   b.uri2 = 'http://dbpedia.org/resource/Matt_Nathanson' OR   b.uri2 = 'http://dbpedia.org/resource/The_Notebook_(2013_film)' OR   b.uri2 = 'http://dbpedia.org/resource/Shutter_Island_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Pirates_of_the_Caribbean:_The_Curse_of_the_Black_Pearl' OR   b.uri2 = 'http://dbpedia.org/resource/Kesha');

select distinct uri2 from `lod`.`link` as b where b.uri1 = 'http://dbpedia.org/resource/Nuestro_Himno'   and (  b.uri2 = 'http://dbpedia.org/resource/The_Beatles' OR   b.uri2 = 'http://dbpedia.org/resource/Drake_(rapper)' OR   b.uri2 = 'http://dbpedia.org/resource/Michael_Jackson' OR   b.uri2 = 'http://dbpedia.org/resource/Erykah_Badu' OR   b.uri2 = 'http://dbpedia.org/resource/Ludacris' OR   b.uri2 = 'http://dbpedia.org/resource/The_Hangover' OR   b.uri2 = 'http://dbpedia.org/resource/Monica_(singer)' OR   b.uri2 = 'http://dbpedia.org/resource/Sam_Sparro' OR   b.uri2 = 'http://dbpedia.org/resource/Rihanna' OR   b.uri2 = 'http://dbpedia.org/resource/The_Temptations_(TV_miniseries)' OR   b.uri2 = 'http://dbpedia.org/resource/Fatal_Attraction' OR   b.uri2 = 'http://dbpedia.org/resource/Basic_Instinct' OR   b.uri2 = 'http://dbpedia.org/resource/Because_I_Said_So_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Baby_Mama_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Titanic_(1997_film)' OR   b.uri2 = 'http://dbpedia.org/resource/Maxwell_(musician)' OR   b.uri2 = 'http://dbpedia.org/resource/Jill_Scott' OR   b.uri2 = 'http://dbpedia.org/resource/Due_Date' OR   b.uri2 = 'http://dbpedia.org/resource/John_Mayer' OR   b.uri2 = 'http://dbpedia.org/resource/Pocahontas_(1995_film)' OR   b.uri2 = 'http://dbpedia.org/resource/He\'s_Just_Not_That_Into_You_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Anthony_Hamilton_(musician)' OR   b.uri2 = 'http://dbpedia.org/resource/Kate_Voegele' OR   b.uri2 = 'http://dbpedia.org/resource/Revolutionary_Road_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Sam_Cooke' OR   b.uri2 = 'http://dbpedia.org/resource/Lady_Gaga' OR   b.uri2 = 'http://dbpedia.org/resource/Matt_Nathanson' OR   b.uri2 = 'http://dbpedia.org/resource/The_Notebook_(2013_film)' OR   b.uri2 = 'http://dbpedia.org/resource/Shutter_Island_(film)' OR   b.uri2 = 'http://dbpedia.org/resource/Pirates_of_the_Caribbean:_The_Curse_of_the_Black_Pearl' OR   b.uri2 = 'http://dbpedia.org/resource/Kesha');


SET SQL_SAFE_UPDATES=0;
select count(*) FROM lod.link WHERE uri1 = uri2;
delete FROM lod.link WHERE uri1 = uri2;
SET SQL_SAFE_UPDATES=1;

delete FROM lod.link WHERE uri1  NOT LIKE CONCAT('http://dbpedia.org/resource/', '%');
delete FROM lod.link WHERE uri2  NOT LIKE CONCAT('http://dbpedia.org/resource/', '%');
select count(*) FROM lod.link WHERE uri1 NOT LIKE CONCAT('http://dbpedia.org/resource/', '%') OR uri2 NOT LIKE CONCAT('http://dbpedia.org/resource/', '%'); 

delete FROM lod.link WHERE uri1 = 'http://dbpedia.org/class/yago/WikicatAmericanGuitaristsXX';
select count(*) FROM lod.link WHERE uri1 LIKE CONCAT('http://dbpedia.org/resource/', '%');
select * FROM lod.link WHERE uri1 LIKE CONCAT('http://dbpedia.org/resource/', '%') LIMIT 5;


SELECT * FROM lod.book where uri LIKE '%http://dbpedia.org/resource/"Weird_Al"_Yankovic%';
SELECT * FROM lod.movie where uri LIKE '%http://dbpedia.org/resource/"Weird_Al"_Yankovic%';
SELECT * FROM lod.book where uri LIKE '%http://dbpedia.org/resource/"Weird_Al"_Yankovic%';


select * FROM lod.movie WHERE uri LIKE '%\"%';
select * FROM lod.book WHERE uri LIKE '%\"%';
select * FROM lod.music WHERE uri LIKE '%\"%';




delete from music WHERE id = 364 or id=3398 or id=5150 or id=6130 or id=6138;
delete from movie WHERE id = 5457;


select * FROM lod.link WHERE uri1 NOT LIKE CONCAT('http://dbpedia.org/resource/', '%') OR uri2 NOT LIKE CONCAT('http://dbpedia.org/resource/', '%') LIMIT 10;

select * FROM lod.link WHERE uri1 = 'http://dbpedia.org/resource/';




