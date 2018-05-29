SET SQL_SAFE_UPDATES=0;
delete FROM lod.evaluation;
delete FROM lod.hitrate;
delete FROM lod.prediction;
delete FROM lod.semantic;
SET SQL_SAFE_UPDATES=1;



select userid from `lod`.`prediction` as b where b.sim = "LDSD" group by userid;

select count(uri) from `lod`.`prediction` as b where b.userid = 5122 and b.sim = "LDSD";
select * from `lod`.`prediction` as b where b.userid = 5122 and b.sim = "LDSD";
select * from `lod`.`prediction` as b where b.userid = 5122 and b.sim = "LDSD_LOD";

select * from `lod`.`prediction` as b where b.sim = "LDSD";
select * from `lod`.`prediction` as b where b.sim = "LDSD_LOD";

select count(*) from `lod`.`prediction` as b where b.sim = "LDSD_LOD" and b.seed = 'SEED_EVALUATION';
select count(*) from `lod`.`prediction` as b where b.sim = "LDSD" and b.seed = 'SEED_EVALUATION';



select userid from `lod`.`prediction` as b where b.sim = "LDSD" and b.original_candidate = b.uri group by userid;
select userid from `lod`.`prediction` as b where b.sim = "LDSD_LOD" and b.original_candidate = b.uri group by userid;

select count(userid) from `lod`.`prediction` as b where b.sim = "LDSD_LOD" and b.original_candidate = b.uri order by userid;
select count(userid) from `lod`.`prediction` as b where b.sim = "LDSD" and b.original_candidate = b.uri order by userid;

select count(userid) from `lod`.`prediction` as b where b.sim = "LDSD_LOD" and b.original_candidate != b.uri order by userid;
select count(userid) from `lod`.`prediction` as b where b.sim = "LDSD" and b.original_candidate != b.uri order by userid;

select * from `lod`.`prediction` as b where b.original_candidate = b.uri order by userid;

select max(userid)  FROM ((select distinct ml.userid from music as m, music_like as ml where  m.id = ml.musicid  )   UNION ALL (select distinct ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid  )   UNION ALL (select distinct ml.userid from book  as m, book_like  as ml where  m.id = ml.bookid   )  ) as x;

SELECT * FROM lod.hitrate;	
SELECT after FROM lod.hitrate;
SELECT * FROM lod.evaluation where original_candidate=uri;

SELECT * FROM lod.evaluation where original_candidate=uri and `sim`= "LDSD_LOD";

SELECT count(*) FROM lod.evaluation where `sim`= "LDSD_LOD" and original_candidate=uri;
SELECT count(*) FROM lod.evaluation where `sim`= "LDSD_LOD";
SELECT count(*) FROM lod.evaluation where `sim`= "LDSD";


SELECT count(original_candidate) FROM lod.evaluation where `sim`= "LDSD_LOD" and userid = (select max(userid)  FROM lod.evaluation where original_candidate=uri ) ;

select max(userid)  FROM lod.evaluation where original_candidate=uri;

select count(original_candidate) from `lod`.`evaluation` as b where b.userid = 1;
select count(original_candidate) from `lod`.`evaluation` as b where b.userid = 458;

select count(original_candidate) from `lod`.`evaluation`   as b where b.userid = 458 and `sim`= "LDSD_LOD" ;
select count(original_candidate) from `lod`.`evaluation` as b where b.userid = 458 and `sim`= "LDSD"   ;


select max(userid)  FROM lod.evaluation where original_candidate=uri;


select max(userid) FROM lod.evaluation where original_candidate=uri;

SET SQL_SAFE_UPDATES=0;
delete FROM lod.evaluation WHERE userid = 458;
SELECT * FROM lod.evaluation;

SELECT * FROM lod.evaluation where uri = 'http://dbpedia.org/resource/The_Man_Who_Cried';

SELECT * FROM lod.evaluation where userid = 179850;

SELECT count(*) FROM lod.evaluation;

select distinct *  FROM ((select distinct m.id, m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid = 200 ) 
  UNION ALL (select distinct m.id, m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid = 200 )
  UNION ALL (select distinct m.id, m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid = 200 )  ) as x;

select distinct userid  FROM ((select distinct count(mu.id) as total, userid  from music as mu, music_like as mul where  mu.id = mul.musicid  group by userid)   UNION ALL (select distinct count(mo.id) as total, userid from movie as mo, movie_like as mol where  mo.id = mol.movieid group by userid)   UNION ALL (select distinct count(bo.id) as total, userid from book  as bo, book_like  as bol where  bo.id = bol.bookid  group by userid)) as x group by userid  having sum(total) > 0 and sum(total) < 5;

select distinct *  FROM ((select distinct m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid = 2008 )
   UNION ALL (select distinct m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid = 2008 ) 
   UNION ALL (select distinct m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid = 2008 )  ) as x ORDER BY RAND() LIMIT 20;


231
337
641
756
816
907
962
1078
1286
1563
1573
1686
1863
1914
1991
2008
2034
2168
2520
2587
2592
2624
2673
2711
2853
2855
2926
2977
3137
3140
3155
3175
3206
3332
3369
3491
3515
3552
3682
3766
3804
4040
4115
4226
4350
4356
4458
4960
5024
5080
5122
5182
5226
5471
5571
5603
6130
6192
6197
6351
6378
6399
6747
6827
6916
6975
7002
7012
7032
7112
7257
7510
7662
7691
7718
7809
7894
8130
8143
8148
8177
8198
8238
8510
8543
8708
8816
8999
9050
9145
9402
9409
9461
9482
9483
9814
9831
9933
9967
10002
10030
10414
10442
10597
10613
10658
10831
10929
10947
11086
11324
11484
11731
11768
12049
12073
12319
12361
12375
12477
12482
12544
12671
12692
12834
12984
13067
13107
13239
13288
13296
13340
13574
13677
13708
13738
14162
14396
14457
14467
14613
15347
15534
15620
15690
15817
15910
15923
15981
16025
16055
16526
16649
16671
16834
17003
17205
17266
17347
17462
17561
17707
17729
17965
17966
18128
18130
18491
18546
18632
18685
18705
18851
18963
19246
19518
19557
19720
19897
19952
20290
20517
20526
20544
20692
20698
20816
20831
20899
20963
21087
21156
21310
21527
21616
21628
21749
21898
21924
21927
21945
22013
22017
22057
22155
22304
22305
22569
22573
22886
22942
22956
23054
23123
23166
23182
23346
23400
23404
23447
23464
23484
23536
23724
23740
23792
24243
24257
24351
24412
24446
24483
24564
24629
24748
25046
25078
25148
25173
25259
25521
25588
25772
25942
26059
26160
26488
26546
26663
26858
27260
27277
27411
27432
27465
27530
27756
27898
28004
28040
28258
28549
28598
28765
28833
28858
28883
29035
29447
29572
29601
29621
29712
29739
29780
29857
30189
30194
30204
30565
30653
30737
30744
30782
30827
30911
30913
31052
31058
31225
31232
31270
31349
31448
31541
31576
31702
31931
31993
32016
32021
32064
32115
32454
32855
32955
33018
33162
33529
33538

SELECT * FROM lod.evaluation;
SELECT count(*) FROM lod.evaluation  WHERE `sim`='LDSD_LOD' and uri = original_candidate ;
SELECT count(*) FROM lod.evaluation  WHERE `sim`='LDSD' and uri = original_candidate ;

OPTIMIZE  TABLE LINK;
OPTIMIZE  TABLE PREDICTION;
OPTIMIZE  TABLE SEMANTIC;


SET SQL_SAFE_UPDATES=0;
delete FROM lod.evaluation;
delete FROM lod.hitrate;
delete FROM lod.prediction;
SET SQL_SAFE_UPDATES=1;

SELECT * FROM lod.hitrate;


SET SQL_SAFE_UPDATES=1;

SET SQL_SAFE_UPDATES=0;
select count(*) FROM lod.link WHERE uri1 = uri2;
delete FROM lod.link WHERE uri1 = uri2;
SET SQL_SAFE_UPDATES=1;

SET SQL_SAFE_UPDATES=0;
select * FROM lod.link WHERE uri1 = "https://www.w3.org/2002/07/owl#Thing";
select count(*) FROM lod.link WHERE uri2 = "https://www.w3.org/2002/07/owl#Thing";

select count(*) FROM lod.link WHERE uri2 = "https://www.w3.org/2002/07/owl#Thing";
select count(*) FROM lod.link WHERE uri1 = uri2;
select * FROM lod.link WHERE uri1 = uri2;

select * FROM lod.link LIMIT 500;





SET SQL_SAFE_UPDATES=1;



SET SQL_SAFE_UPDATES=0;
delete FROM lod.hitrate WHERE lod.hitrate.after = 1;
SET SQL_SAFE_UPDATES=1;

SELECT distinct userid FROM lod.evaluation where uri=original_candidate order by userid ;


SELECT * FROM lod.evaluation where `sim`='LDSD' and original_candidate=uri order by userid;
SELECT *  FROM lod.evaluation where `sim`='LDSD_LOD' and original_candidate=uri order by userid;
SELECT max(userid) FROM lod.evaluation where `sim`='LDSD_LOD' and original_candidate=uri order by userid;
SELECT max(userid) FROM lod.evaluation where `sim`='LDSD' and original_candidate=uri order by userid;

SELECT count(*) FROM lod.evaluation;

SELECT * FROM lod.evaluation where `sim`='LDSD_LOD' and original_candidate!=uri;
SELECT * FROM lod.evaluation where `sim`='LDSD' and original_candidate!=uri;


SELECT * FROM lod.evaluation where `sim`='LDSD_LOD' order by position;
SELECT * FROM lod.evaluation where `sim`='LDSD' and original_candidate!=uri;
