package com.example.helpworx.sr.repository;


import com.example.helpworx.ctmmny.domain.Ctmmny;
import com.example.helpworx.ctmmny.domain.QCtmmnySys;
import com.example.helpworx.ctmmny.domain.QSys;
import com.example.helpworx.sr.domain.QSr;
import com.example.helpworx.sr.dto.QSrDto_ListResponse;
import com.example.helpworx.sr.dto.SrDto;
import com.example.helpworx.sr.dto.response.*;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class SrRepositoryImpl implements SrRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long deleteByCtmmnyCd(String ctmmnyCd) {
        QSr sr = QSr.sr;

        return queryFactory
                .delete(sr)
                .where(
                        eqCtmmnyCd(ctmmnyCd)
                )
                .execute();
    }

    @Override
    public Long findAverageRcptTime(String ctmmnyCd, String name) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(
                        Expressions.numberTemplate(Long.class, "COALESCE( AVG( (EXTRACT(EPOCH FROM {0} -{1})/3600)), 0)", sr.rcptTreat.rcptTim, sr.reqTim)
                )
                .from(sr)
                .where(
                        eqCtmmnyCd(ctmmnyCd),
                        eqSysNm(name)
                )
                .fetchOne();
    }
    @Override
    public Long findAverageTreatTime(String ctmmnyCd, String name) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(
                        Expressions.numberTemplate(Long.class, "COALESCE( AVG( (EXTRACT(EPOCH FROM {0} -{1})/3600)), 0 )", sr.rcptTreat.treatEndTim, sr.rcptTreat.treatStartTim)
                )
                .from(sr)
                .where(
                        eqCtmmnyCd(ctmmnyCd),
                        eqSysNm(name)
                )
                .fetchOne();
    }

    @Override
    public List<SrRankDTO> findRankListBySysNm(String name) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(new QSrRankDTO(
                        sr.ctmmny.ctmmnyNm,
                        sr.count()
                ))
                .from(sr)
                .where(
                        eqSysNm(name)
                )
                .groupBy(sr.ctmmny.ctmmnyNm)
                .fetch();
    }

    @Override
    public List<OperatorIncompleteSrDTO> findIncompleteSrList(String id) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(new QOperatorIncompleteSrDTO(
                        sr.id,
                        sr.sysNm,
                        sr.sbjct,
                        sr.reqr.userNm,
                        sr.reqTim,
                        sr.status
                ))
                .from(sr)
                .where(
                        eqCtmmnyCd(id),
                        notEqStatus("해결")
                )
                .orderBy(sr.reqTim.asc())
                .fetch();
    }

    @Override
    public List<OperatorProceedingSrDTO> findProceedingSrList(String id) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(new QOperatorProceedingSrDTO(
                        sr.id,
                        sr.sysNm,
                        sr.reqr.userNm,
                        sr.reqTim
                ))
                .from(sr)
                .where(
                        eqCtmmnyCd(id),
                        eqStatus("해결")
                        // 이부분 수정해야함ㅋ!!!!
                )
                .orderBy(sr.reqTim.asc())
                .fetch();
    }

    @Override
    public List<CustomerIncompleteCtmmnySrDTO> findIncompleteSrListByCtmmnyId(String id){
        QSr sr = QSr.sr;
        return queryFactory
                .select(new QCustomerIncompleteCtmmnySrDTO(
                        sr.id,
                        sr.sysNm,
                        sr.sbjct,
                        sr.reqr.userNm,
                        sr.reqTim,
                        sr.status
                ))
                .from(sr)
                .where(
                        eqCtmmnyCd(id),
                        notEqStatus("해결")
                )
                .orderBy(sr.reqTim.asc())
                .fetch();
    }

    @Override
    public List<CustomerIncompleteUserSrDTO> findIncompleteSrListByUserId(Long id) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(new QCustomerIncompleteUserSrDTO(
                        sr.id,
                        sr.sysNm,
                        sr.sbjct,
                        sr.reqTim,
                        sr.status))
                .from(sr)
                .where(
                        eqReqr(id),
                        notEqStatus("해결"))
                .orderBy(sr.reqTim.asc())
                .fetch();
    }

    @Override
    public List<SystemPairResponseDTO> findIdAndNameByName() {
        QSys sys = QSys.sys;
        return queryFactory
                .select(new QSystemPairResponseDTO(
                        sys.id,
                        sys.sysNm
                ))
                .from(sys)
                .fetch();
    }

    @Override
    public List<SrDto.ListResponse> findSrListWithCondition(Pageable pageable, SrDto.SrsListRequest srListRequestDto) {
        QSr sr = QSr.sr;

        return queryFactory
                .select(new QSrDto_ListResponse(
                        sr.id,
                        sr.status,
                        sr.sbjct,
                        sr.reqr.userNm,
                        sr.reqTim,
                        sr.sysNm,
                        sr.jobGb,
                        sr.reqGb,
                        sr.rcptTreat,
                        sr.ctmmny.ctmmnyNm
                ))
                .from(sr)
                .where(eqSysNm(srListRequestDto.getSysNm()),
                        eqJobGb(srListRequestDto.getJobGb()),
                        eqReqr(srListRequestDto.getId()),
                        eqReqGb(srListRequestDto.getReqGb()),
                        eqStatus(srListRequestDto.getStatus()),
                        sr.sbjct.contains(srListRequestDto.getSbjct()),
                        sr.dscr.contains(srListRequestDto.getDscr()),
                        eqCtmmnyCd(srListRequestDto.getCtmmnyCd())

                ).orderBy(sr.id.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

    }
    @Override
    public List<OperatorTreatListByMonthDTO> findTreatListByCtmmnyAndSysNm(String CtmmnyCd, String SysNm) {
        QSr sr = QSr.sr;
        StringTemplate formattedDate = Expressions.stringTemplate(
                "to_char({0}, {1})",sr.rcptTreat.treatStartTim, ConstantImpl.create("Month"));
        StringPath nameAlias = Expressions.stringPath("treat_tim");
        return queryFactory
                .select(
                        new QOperatorTreatListByMonthDTO(
                                formattedDate.as("treat_tim"),
                                Expressions.numberTemplate(Long.class, " COALESCE( SUM( (EXTRACT(EPOCH FROM {0} -{1})/3600) ), 0 )", sr.rcptTreat.treatEndTim, sr.rcptTreat.treatStartTim)
                        )
                )
                .from(sr)
                .where(eqCtmmnyCd(CtmmnyCd),
                        eqSysNm(SysNm)
                )
                .groupBy(
                        nameAlias
                )
                .orderBy(nameAlias.asc())
                .fetch();
    }

    @Override
    public List<OperatorChartListByMonthDTO> findSrListByCtmmnyAndSysNm(String CtmmnyCd, String SysNm) {
        QSr sr = QSr.sr;
        StringTemplate formattedDate = Expressions.stringTemplate(
                "to_char({0}, {1})",sr.reqTim, ConstantImpl.create("Month"));
        StringPath nameAlias = Expressions.stringPath("req_tim");
        return queryFactory
                .select(
                        new QOperatorChartListByMonthDTO(
                            formattedDate.as("req_tim"),
                            Wildcard.count)
                )
                .from(sr)
                .where(eqCtmmnyCd(CtmmnyCd),
                        eqSysNm(SysNm)
                )
                .groupBy(
                        nameAlias
                )
                .orderBy(nameAlias.asc())
                .fetch();
    }

    @Override
    public Long countByCtmmnyAndSysNmAndReqGb(Ctmmny ctmmny, String name, String reqGb) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(sr.count())
                .from(sr)
                .where(eqCtmmny(ctmmny),
                        eqSysNm(name),
                        eqReqGb(reqGb))
                .fetchCount();
    }

    @Override
    public Long countByCtmmnyCdAndSysNmAndReqGb(String ctmmnyCd, String name, String reqGb) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(Wildcard.count)
                .from(sr)
                .where(eqCtmmnyCd(ctmmnyCd),
                        eqSysNm(name),
                        eqReqGb(reqGb)
                )
                .fetchCount();
    }

    @Override
    public Long countByCtmmnyCdAndSysNmAndMonth(String ctmmnyCd, String name, Integer month) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(sr.reqTim.count())
                .from(sr)
                .where(eqCtmmnyCd(ctmmnyCd),
                        eqSysNm(name),
                        eqMonth(month))
                .fetchCount();
    }

    @Override
    public Long countByCtmmnyAndSysNmAndMonth(Ctmmny ctmmny, String name, Integer month) {
        QSr sr = QSr.sr;
        return queryFactory
                .select(sr.reqTim.count())
                .from(sr)
                .where(eqCtmmny(ctmmny),
                        eqSysNm(name),
                        eqMonth(month))
                .fetchCount();
    }
    private BooleanExpression eqCtmmnyNm(String name){
        return !StringUtils.isEmpty(name) ? QSr.sr.ctmmny.ctmmnyNm.eq(name) : null;
    }
    private BooleanExpression eqCtmmnyCd(String id){
        return !StringUtils.isEmpty(id) ? QSr.sr.ctmmny.id.eq(id) : null;
    }

    private BooleanExpression notEqStatus(String status){
        return !StringUtils.isEmpty(status) ? QSr.sr.status.notIn(status) : null;
    }

    private BooleanExpression eqReqr(Long id){
        return !StringUtils.isEmpty(id) ? QSr.sr.reqr.id.eq(id) : null;
    }

    private BooleanExpression eqCtmmny(Ctmmny ctmmny){
        return !StringUtils.isEmpty(ctmmny) ? QSr.sr.ctmmny.eq(ctmmny) : null;
    }

    private BooleanExpression eqMonth(Integer month){
        return !StringUtils.isEmpty(month) ? QSr.sr.reqTim.month().eq(month) : null;
    }

    private BooleanExpression eqSysNm(String sysNm){
        return !StringUtils.isEmpty(sysNm) ? QSr.sr.sysNm.eq(sysNm) : null;
    }
    private BooleanExpression eqJobGb(String jobGb){
        return !StringUtils.isEmpty(jobGb) ? QSr.sr.jobGb.eq(jobGb) : null;
    }

    private BooleanExpression eqId(Long id) {
        return !StringUtils.isEmpty(id) ? QSr.sr.id.eq(id) : null;
    }

    private BooleanExpression eqReqGb(String reqGb) {
        return !StringUtils.isEmpty(reqGb) ? QSr.sr.reqGb.eq(reqGb) : null;
    }

    private BooleanExpression eqStatus(String status) {
        return !StringUtils.isEmpty(status) ? QSr.sr.status.eq(status) : null;
    }

    private BooleanExpression eqSbjct(String sbjct){
        return !StringUtils.isEmpty(sbjct) ? QSr.sr.sbjct.like(sbjct) : null;
    }

    private BooleanExpression eqDscr(String dscr) {
        return !StringUtils.isEmpty(dscr) ? QSr.sr.dscr.like(dscr) : null;
    }
}
