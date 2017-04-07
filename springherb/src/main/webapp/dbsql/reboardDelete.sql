--reboardDelete.sql
create or replace procedure reboardDelete --���ν��� �̸� 
(
--�Ű�����
    p_no  number,
    p_groupNo number,
    p_step    number
)
is
--���������
    cnt number;
begin
--ó���� ����
    --�亯�� �޸� �������� ��� delflag='Y' �� update�ϱ�
    
    --[1] �������� ���
    if p_step=0 then
        select count(*) into cnt from reboard
        where groupno=p_groupno;
        
        --�亯�� �޸� ��� - update
        if cnt>1 then
            update reboard
            set delflag='Y'
            where no=p_no;
        else
        --�亯�� �޸��� ���� ���
            delete from reboard where no=p_no;
        end if;
    else
    --[2] �亯���� ���
        delete from reboard where no=p_no;
    end if;
    
    commit;

EXCEPTION
    WHEN OTHERS THEN
	raise_application_error(-20001, '�亯�� �Խ��� -�ۻ��� ����!');
    ROLLBACK;
end;

/*
    exec reboardDelete(14,14,0);
*/
