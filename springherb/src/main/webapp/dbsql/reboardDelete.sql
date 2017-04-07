--reboardDelete.sql
create or replace procedure reboardDelete --프로시저 이름 
(
--매개변수
    p_no  number,
    p_groupNo number,
    p_step    number
)
is
--변수선언부
    cnt number;
begin
--처리할 내용
    --답변이 달린 원본글인 경우 delflag='Y' 로 update하기
    
    --[1] 원본글인 경우
    if p_step=0 then
        select count(*) into cnt from reboard
        where groupno=p_groupno;
        
        --답변이 달린 경우 - update
        if cnt>1 then
            update reboard
            set delflag='Y'
            where no=p_no;
        else
        --답변이 달리지 않은 경우
            delete from reboard where no=p_no;
        end if;
    else
    --[2] 답변글인 경우
        delete from reboard where no=p_no;
    end if;
    
    commit;

EXCEPTION
    WHEN OTHERS THEN
	raise_application_error(-20001, '답변형 게시판 -글삭제 실패!');
    ROLLBACK;
end;

/*
    exec reboardDelete(14,14,0);
*/
