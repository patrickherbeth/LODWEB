SELECT * FROM lod.prediction WHERE `sim`='LDSD_LOD';
SELECT * FROM lod.prediction WHERE `sim`='LDSD';

SELECT count(*) FROM lod.prediction WHERE `sim`='LDSD_LOD' and seed != 'SEED_EVALUATION'  and graph_structure >=1 ;
SELECT count(*) FROM lod.prediction WHERE `sim`='LDSD'     and seed != 'SEED_EVALUATION'  and graph_structure >=1 ;


SELECT count(*) FROM lod.prediction WHERE `sim`='LDSD'     and  graph_structure = 0 ;
SELECT count(*) FROM lod.prediction WHERE `sim`='LDSD_LOD' and  graph_structure = 0 ;

SELECT count(*) FROM lod.prediction WHERE `sim`='LDSD'     and graph_structure = 1 ;
SELECT count(*) FROM lod.prediction WHERE `sim`='LDSD_LOD' and graph_structure = 1 ;

SELECT count(*) FROM lod.prediction WHERE `sim`='LDSD'     and graph_structure = 2 ;
SELECT count(*) FROM lod.prediction;

SELECT count(*) FROM lod.prediction WHERE `sim`='LDSD_LOD' and graph_structure = 2 ;



SELECT count(original_candidate),userid FROM lod.prediction WHERE `sim`='LDSD_LOD' and seed = 'SEED_EVALUATION'  group by userid desc;
SELECT count(distinct userid) FROM lod.prediction WHERE `sim`='LDSD' and seed = 'SEED_EVALUATION';

SET SQL_SAFE_UPDATES=0;delete FROM lod.onlineevaluation;delete FROM lod.prediction;delete FROM lod.semantic;SET SQL_SAFE_UPDATES=1;

delete FROM lod.prediction where userid=76 or userid=17 or userid=39 or userid=107 or userid=108 or userid=113 or userid=116 or userid=125 or userid=131 or userid=134 or userid=152 or userid=181 or userid=191 or userid=275 or userid=277 or userid=279 or userid=299 or userid=300 or userid=311 or userid=328 or userid=406 or userid=449 or userid=452 or userid=463 or userid=179839;


delete FROM lod.prediction where lod.prediction.userid=220;
delete FROM lod.prediction where lod.prediction.userid=234;
delete FROM lod.prediction where lod.prediction.userid=238;
delete FROM lod.prediction where lod.prediction.userid=247 or userid=238;
delete FROM lod.prediction where lod.prediction.userid=605 or userid=80853 or userid=164166 or userid=179850;
delete FROM lod.prediction where lod.prediction.userid=80853;
delete FROM lod.prediction where lod.prediction.userid=164166;
delete FROM lod.prediction where lod.prediction.userid=179850;



SELECT   userid  FROM lod.prediction WHERE `sim`='LDSD_LOD' and seed = 'SEED_EVALUATION'  group by userid having count(original_candidate)>51;
SELECT   userid  FROM lod.prediction WHERE `sim`='LDSD' and seed = 'SEED_EVALUATION'  group by userid having count(original_candidate)>51;

(SELECT userid FROM (select userid FROM lod.prediction as b WHERE b.seed = 'SEED_EVALUATION'  group by b.userid having count(b.original_candidate)>102) x);
SET SQL_SAFE_UPDATES=0;
delete e.* from prediction e  where userid in (SELECT userid FROM (select userid FROM lod.prediction as b WHERE b.seed = 'SEED_EVALUATION'  group by b.userid having count(b.original_candidate)>102) x);
SET SQL_SAFE_UPDATES=1;


SELECT count(original_candidate) FROM lod.prediction where  `sim`='LDSD_LOD' and seed = 'SEED_EVALUATION' and `userid`= 1;

SELECT count(original_candidate),userid FROM lod.prediction where seed = 'SEED_EVALUATION' and userid = (select max(userid)  FROM lod.prediction where original_candidate=uri and seed = 'SEED_EVALUATION' );

select min(userid),uri,userid FROM lod.prediction as b WHERE b.seed = 'SEED_EVALUATION'  group by b.userid having count(b.original_candidate)<103;

select userid FROM lod.prediction as b WHERE b.seed = 'SEED_EVALUATION'  group by b.userid having count(b.original_candidate)<102;

SELECT count(original_candidate),userid FROM lod.prediction where userid = (select min(userid)  FROM lod.evaluation where original_candidate=uri;

SELECT min(userid),userid FROM prediction where seed = 'SEED_EVALUATION' group by userid having count(original_candidate)<103;

select max(userid)  FROM lod.prediction where original_candidate=uri and seed = 'SEED_EVALUATION';

select distinct userid FROM lod.prediction where original_candidate=uri and seed = 'SEED_EVALUATION';


select distinct *  FROM ((select distinct m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid <> 179849 )   UNION ALL (select distinct m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid <> 179849 )   UNION ALL (select distinct m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid <> 179849 )  ) as x ORDER BY RAND() LIMIT -1








delete from prediction  WHERE b.seed = 'SEED_EVALUATION' count(b.original_candidate)>102);










SELECT score FROM lod.prediction where `sim`='LDSD'     group by userid;
SELECT score FROM lod.prediction where `sim`='LDSD_LOD'     group by userid;



SELECT count(*) FROM lod.prediction;