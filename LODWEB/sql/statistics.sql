select count( lod.music.id) from lod.music where lod.music.id in (SELECT distinct lod.music_like.musicid FROM lod.music_like);

select count(distinct a.id) from lod.movie a, lod.movie_like b where a.id = b.movieid ;
select count(distinct a.id) from lod.music a, lod.music_like b where a.id = b.musicid ;
select count(distinct a.id) from lod.book a, lod.book_like b where a.id = b.bookid ;


select count(lod.music.id) from lod.music where lod.music.id in (SELECT distinct lod.music_like.musicid FROM lod.music_like);


select avg(x) 'Average Rating' from (SELECT distinct userid, count(*) AS x FROM lod.music_like group by lod.music_like.userid ) avgs;
select avg(y) 'Average Rating' from (SELECT distinct userid, count(*) AS y FROM lod.book_like group by lod.book_like.userid ) avgs;
select avg(z) 'Average Rating' from (SELECT distinct userid, count(*) AS z FROM lod.movie_like group by lod.movie_like.userid ) avgs;

select count(total) from (select  userid,total  FROM (
 (select  count(mul.musicid) as total, userid  from music as mu, music_like as mul where  mu.id = mul.musicid  group by userid having sum(total) = 25  ) 
 UNION  
 (select  count(mol.movieid) as total2, userid from movie as mo, movie_like as mol where  mo.id = mol.movieid  group by userid having sum(total2) = 25 )  
  UNION  
 (select  count(bol.bookid) as total3, userid from book  as bo, book_like  as bol where  bo.id = bol.bookid  group by userid having sum(total3) = 25 ) 
  ) 
  as x group by userid  ) as t; 
  
  
  
  
  select sum(aa) from
  (
  
 (select count(*) as aa from (select  userid  FROM (
 (select  count(mul.musicid) as total, userid  from music as mu, music_like as mul where   mu.id = mul.musicid  group by userid ) ) 
  as x group by userid  having sum(total) = 25 and music_like.userid = 1) as t)
  
  UNION 
 
 (select count(*) as bb from (select  userid  FROM (
 (select  count(mol.movieid) as total2, userid from movie as mo, movie_like as mol where    mo.id = mol.movieid  group by userid ) ) 
  as i group by userid  having sum(total2) = 25 ) as w)
  
  ) as gg
 ;
  
  
  
  
  
  
  
  
  
  
  select count(*) from (select distinct userid, count(bol.bookid) as total from book  as bo, book_like  as bol where  bo.id = bol.bookid   group by userid) as b;
  


SELECT count(distinct lod.music_like.musicid) FROM lod.music_like;
SELECT count(distinct lod.book_like.bookid) FROM lod.book_like;
SELECT count(distinct lod.movie_like.movieid) FROM lod.movie_like;

SELECT count(distinct lod.music_like.userid) FROM lod.music_like;
SELECT count(distinct lod.book_like.userid) FROM lod.book_like;
SELECT count(distinct lod.movie_like.userid) FROM lod.movie_like;

SELECT distinct lod.book_like.bookid FROM lod.book_like where lod.book_like.bookid not in (SELECT distinct lod.book.id FROM lod.book) ;

SELECT count(lod.movie_like.movieid) FROM lod.movie_like;
SELECT count(*) FROM lod.movie;
SELECT count(lod.movie_like.userid) FROM lod.movie_like;
SELECT count(*) FROM lod.movie_like;

SELECT count(*)   FROM lod.music_like as x group by lod.music_like.userid ;


SELECT lod.music_like.userid as userid, count(distinct lod.music_like.musicid) as x FROM lod.music_like group by lod.music_like.userid;

SELECT distinct count( lod.music_like.musicid) FROM lod.music_like;
SELECT  count( *) FROM lod.movie_like;
SELECT distinct count( lod.book_like.bookid) FROM lod.book_like;


SELECT * FROM lod.music_like;

SELECT count(uri) FROM lod.music;
SELECT count(uri) FROM lod.movie;
SELECT count(uri) FROM lod.book;


select   count(*)  FROM ((select distinct ml.userid from music as m, music_like as ml where  m.id = ml.musicid  )   UNION  (select distinct ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid  )  ) as x;

select   count(*)  FROM ((select distinct ml.userid from music_like as ml  )   UNION  (select distinct ml.userid from  movie_like as ml  )   UNION  (select distinct ml.userid from book_like  as ml )  ) as x;

select   count(*)  FROM ((select distinct ml.userid from music as m, music_like as ml where  m.id = ml.musicid  )   UNION  (select distinct ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid  )   UNION  (select distinct ml.userid from book  as m, book_like  as ml where  m.id = ml.bookid   )  ) as x;


select   count(*)  FROM ((select distinct ml.userid from music as m, music_like as ml where  m.id = ml.musicid  )   UNION ALL (select distinct ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid  )   UNION ALL (select distinct ml.userid from book  as m, book_like  as ml where  m.id = ml.bookid   )  ) as x;

select  count(*)  FROM ((select distinct ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid  )   UNION ALL (select distinct ml.userid from book  as m, book_like  as ml where  m.id = ml.bookid   )  ) as x;

select  count(*)  FROM ((select distinct ml.userid from music as m, music_like as ml where  m.id = ml.musicid  )   UNION ALL (select distinct ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid  )   ) as x ;


select  count(distinct mul.userid)  FROM  music_like as mul, movie_like as mol,  book_like  as bol where  mul.userid = mol.userid and mol.userid = bol.userid and mul.userid = bol.userid;
select  count(distinct mul.userid)  FROM  music_like as mul, movie_like as mol where  mul.userid = mol.userid ;
select  count(distinct mul.userid)  FROM  music_like as mul, book_like  as bol where   mul.userid = bol.userid;
select  count(distinct mol.userid)  FROM  movie_like as mol,  book_like  as bol where  mol.userid = bol.userid ;
