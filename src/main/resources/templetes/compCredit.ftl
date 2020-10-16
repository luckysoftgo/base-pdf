<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>企业信用报告</title>
    <style type="text/css">
        /*body,div,p{font-family: "Microsoft YaHei", "SimHei", "Arial"}*/
        p {
            margin: 0;
            padding: 0;
        }

        .page {
            margin: 2cm 1cm;
        }

        .first-page {
            font-weight: bold;
            font-size: 16px;
        }

        .info {
            line-height: 50px;
        }

        .title {
            text-align: center;
            font-size: 26px;
            margin-top: 40px;
            font-weight: 900;
            margin-bottom: 10px;
        }

        .office {
            text-align: center;
            font-size: 56px;
            margin-top: 360px;
            margin-bottom: 20px;
            font-weight: bold;

        }

        .t_text {
            font-size: 16px;
            line-height: 30px;
            font-weight: normal;
            /* margin: 180px 100px 0 100px; */
            border-bottom: 2px solid rgba(0, 0,0,0.1);;
            position: relative;
        }

        .t_text::after{
            content: '';
            width: 100%;
            position: absolute;
            bottom: 3px;
            right: 0px;
            background: rgba(0, 0,0,0.4);
            height: 4px;
        }

        .t_text b {
            line-height: 40px;
        }

        .table_bottom {
            margin-top: 560px;
            text-align: center;
            padding-bottom: 20px;
        }

        #basic {
            margin-top: 350px;
        }

        .first-title {
            text-align: left;
            font-size: 22px;
            margin-top: 50px;
            margin-bottom: 50px;
        }

        table {
            border-collapse: collapse;
            border-spacing: 0;
            border-left: 0.5px solid #c4c5be;
            border-top: 0.5px solid #c4c5be;
        }

        table tr td {
            border-right: 0.5px solid #c4c5be;
            border-bottom: 0.5px solid #c4c5be;
            text-align: center;
        }

        .content {
            font-size: 18px;
            width: 620px;
        }

        .first-td {
            width: 80px;
        }

        .second-td {
            width: 320px;
        }

        .first_table_tr {
            height: 40px;
        }

        .first_table {
            width: 600px;
            table-layout: fixed;
            word-break: break-strict;
        }

        .one_name {
            width: 80px;
        }

        .grey_td {
            background-color: #EAEBE4;
        }

        .info_table_size {
            width: 100%;
            table-layout: fixed;
            word-break: break-strict;
            font-size: 14px;
        }

        .info_table_size .dead_tr td {
            padding: 10px 12px;
            font-size: 14px;
            text-align: left;
        }

        .info_table_size .dead_tr .bolder_word {
            text-align: center;
        }

        .info_table_size .live_tr .xuhao {
            padding: 14px 2px;
            width: 32px;
        }

        .col_title {
            padding: 14px 2px;
        }

        .col_title_bg {
            padding: 6px 0px;
            width: 180px;
        }

        .col_title_year {
            width: 36px;
        }

        table tr .bolder_word {
            font-weight: 900;
            text-align: center;
        }

        .title_big {
            font-weight: 900;
            font-size: 20px;
            text-align: left;
            background: #758D30;
            color: #ffffff;
            padding: 6px 20px;
            width: auto;
            display: inline-block;
        }

        .title_small {
            width: 100%;
            font-weight: 900px;
            font-size: 16px;
            margin-top: 20px;
            margin-bottom: 12px;
            padding-left: 20px;
            text-align: left;
        }

        .force_paging {
            page-break-after: always;
        }

        .surround_div {
            width: 100%;
        }

        @page {
            size: 210mm 297mm;
            margin: 0.25in;
            -fs-flow-bottom: "footer";
            -fs-flow-left: "left";
            -fs-flow-right: "right";
            padding: 1em;
        }

        @page :right {
            @bottom-center {
                content: counter(page)"/"counter(pages);

            }

        }

        @page :left {
            @bottom-center {
                content: counter(page)"/"counter(pages);

            }

        }

        #footer {
            font-weight: bolder;
            font-size: 100%;
            position: absolute;
            top: 0;
            left: 0;
            -fs-move-to-flow: "footer";
        }

        #pagenumber:before {
            content: counter(page);
        }

        #pagecount:before {
            content: counter(pages);
        }

        E::before {
            content: counter(title) ；
        }

        .title_big {
            counter-increment: counter_h1;
            counter-reset: counter_h2;
        }

        .title_big::before {
            content: counter(counter_h1) ".";
        }

        .title_small {
            counter-increment: counter_h2;
        }

        .title_small::before {
            content: counter(counter_h1) "."counter(counter_h2);
        }

        .contents {
            page-break-after: always;
        }

        .contents ul li {
            line-height: 28px;
            font-size: 14px;
        }

        .fengmian {
            page-break-after: always;
        }

        .none_type_div {
            width: 600px;
            font-size: 20px;
            padding-left: 50px;
        }

        .shuoming {
            width: 100%;
        }

        .shuoming_title {
            font-size: 35px;
            font-weight: 900;
            text-align: center;
            padding-top: 60px;
        }

        strong {
            font-weight: bolder;
        }

        .shuoming_content {
            margin-top: 20px;
        }

        .shuoming_content p {
            text-indent: 28px;
            margin: 0 40px;
            padding: 0 20px 16px 40px;
            line-height: 28px;
            text-align: left;
            font-size: 14px;
            background: url("${imagePath}contentinfo_li.jpg") no-repeat left 5px;
        }

        .signature {
            font-size: 14px;
            font-weight: 900;
            margin-top: 120px;
            padding-right: 65px;
            float: right;
        }

        .title {
            <#--background:url('${imagePath}one-top.jpg') left -20px no-repeat;-->
            width: 100%;
            background-size: 100% 100%;
        }

        .green_table1 {
            width: 100%;
            table-layout: fixed;
            word-break: break-strict;
        }

        .green_table,
        .green_table tr td {
            border: 0;
            vertical-align: top;
        }

        .green_table1,
        .green_table1 tr td {
            border: 0;
            text-align: center;
            padding: 10px 12px;
            font-size: 14px;
        }

        .green_table1 tr th {
            background-color: #e9eae5;
            font-weight: 900;
            padding: 10px 12px;
            font-size: 14px;
            border-top: 0.5px solid #758D30;
            border-bottom: 0.5px solid #ccc;
        }

        .green_table1 tr td {
            border-bottom: 0.5px solid #ccc;
        }
        .catalog-list {
            padding: 0 30px;
            margin: 0;
            margin-bottom: 100px;
            font-size: 18px;

        }

        .catalog-list li {
            padding: 0;
            margin: 0;
            list-style-type: none;
            padding: 10px 0;
            overflow: hidden;
        }

        .catalog-list li .line {
            border-bottom: 1px dashed #dddddd;
            margin-top: 10px;
        }

        .catalog-list .catalog-title {
            display: inline-block;
            background-color: #ffffff;
            float: left;
            padding-right: 20px;

        }

        .catalog-list .child .catalog-title {
            padding-left: 2em;
        }
    </style>
</head>

<body style="font-family: SimSun; margin: 0px; padding: 0px;">
    <div class="page" style="margin: 0px; padding: 0px;">
        <!-- 报告封面开始 -->
        <div style="background: url('file:///${imagePath}one-bown.jpg') bottom no-repeat;width:100%;">
            <div class="first-page">
                <div class="t_text"><b>报告编号:</b><span>${reportNo}</span></div>
                <!-- <div class="info"></div> -->
                <!-- <div style="background:#7C962D;float: left; font-size: 16px; padding:5px 15px; color:#ffffff;border-radius:8px;margin-top:320px;margin-left:200px;">${reportVerson}</div> -->
                <div class="title" style="text-align: center;background: none;margin-top: 300px;font-weight: bolder;font-size: 32px;">${companyName}</div>
                <div class="title">征信报告</div>
                <!-- <div class="t_text"><b>完成状态:</b><span>最终报告</span></div> -->
            </div>
            <div class="fengmian">
                <div class="table_bottom">
                    <b>报告日期:</b><span>${reportDate}</span>
                </div>
            </div>
        </div>
        <!-- 报告封面结束 -->

        <div style="page-break-after: always;">
            <!-- 报告说明开始 -->
            <div class="t_text">
            </div>
            <div class="shuoming">
                <div class="shuoming_title"><strong>声明</strong></div>
                <div class="shuoming_content">
                    <p>一、我市地方金融工作局（以下简称“我金融局”）经${companyName}（以下简称“企业”）授权获取到其相关信息，并由我金融局运营的我市中小企业融资服务平台（以下简称“平台”）对企业出具信用信息报告。除因本次报告事项我金融局与报告所涉主体构成委托关系外，我金融局与其不存在任何影响监测行为独立、客观、公正的关联关系。</p>
                    <p>二、本报告构成商业机密，未经我金融局及平台同意，任何机构或个人不得以任何形式将本报告全部或部分提供给第三人，不得发表、修改、歪曲、篡改、发行、展示、改编本报告全部或部分内容，不得以任何形式复制、转发或公开传播本报告全部或部分内容，不得将本报告全部或部分内容用于营利或未经允许的其他任何用途，我金融局及平台将保留采取一切法律手段追究其法律责任的权利。</p>
                    <p>三、本报告仅适用于国内市场，用于相关决策参考，并非是某种决策的结论、建议等</p>
                    <p>四、除非特别声明，本报告信息未经复核，我金融局力求但不保证报告数据的准确性、时效性和完整性，但承诺在信息汇总、整合的过程中保持客观、中立的原则。</p>
                    <p>五、在任何情况下，我金融局不对因使用报告而产生的任何后果承担法律责任，不承担因此而引起的损失和损害。</p>
                    <p>六、我金融局保留不同时期或由于某些原因，对企业报告的修改权、实时更新权、以及对企业信息异议的更正权。</p>
                    <!-- <p>信息主体有权对本报告中的内容提出异议。</p> -->
                    <!-- <p>小猫钓鱼有限公司是在中国人民银行备案的合法企业征信机构，备案信息请登录:<br />http://xian.pbc.gov.cn/xian/110/2730984/index.html进行查询，业务咨询电话：<br />029-119。</p> -->
                </div>
                <!-- <div class="signature">征信机构(签章):小猫钓鱼有限公司</div> -->
            </div>
            <!-- 报告说明结束 -->
        </div>

        <!-- 目录开始 -->
        <!--
    <div class="title1" id="catalog">
        <div>
            <img src="${imagePath}contenginfo_01.jpg" style="width: 100%;height: 60px;padding: 0;margin:0;"></img>
        </div>
        <div class="contents" style="text-align: center;">
            <div style="padding-top: 38px;"></div>
            <div style="height: 100px;width:440px;padding: 0 0 0 30px;margin: 0;">
                <img src="${imagePath}list-bt.png" style="width: 440px;height: 100px;padding: 0;margin: 0;"></img>
            </div>
            <ul id="catal"></ul>
        </div>
    </div>
    -->
        <!-- 目录结束 -->
        <div class="catalog-content">
            <h1>目录</h1>
            <ul class="catalog-list">
                <li>
                    <span class="catalog-title">1 基本信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">1.1 登记信息</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">2 企业登记信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">2.1 出资人及出资信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">2.2 分支机构信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">2.3 对外投资</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">2.4 变更记录</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">2.5 资质信息</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">3 法人高管信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">3.1 主要管理人员信息</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">4 行政监管信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.1 监管信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.2 经营异常信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.3 行政许可信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.4 行政处罚信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.5 行政奖励信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.6 守信激励</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.7 失信惩戒</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">5 司法信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">5.1 立案信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">5.2 开庭公告</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">5.3 法律诉讼信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">5.4 被执行人信息</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">6 认定名录</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">6.1 招投标信息</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">7 知识产权</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">7.1 专利信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">7.2 商标信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">7.3 著作权信息</span>
                    <div class="line"></div>
                </li>
            </ul>
        </div>
        <!-- 登记信息 -->
        <!-- 栏目大标题开始 -->
        <h1 class="title_big">基本信息</h1>
        <!-- 栏目大标题结束 -->
        <!-- 小标题开始 -->
        <h1 class="title_small">登记信息</h1>
        <!-- 小标题结束 -->
        <!-- 企业基本信息开始 -->
        <div class="surround_div force_paging">
            <table class="info_table_size">
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">企业名称</td>
                    <td class="bolder_word" colspan="3" style="text-align: left">${baseInfo.companyName}</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">法定代表人</td>
                    <td>${baseInfo.legalPersonName!'-'}</td>
                    <td class="one_name grey_td bolder_word">注册资本</td>
                    <td>${baseInfo.regCapital!'-'}万元</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">注册时间</td>
                    <td>${baseInfo.estiblishTime!'-'}</td>
                    <td class="one_name grey_td bolder_word">登记状态</td>
                    <td>${baseInfo.regStatus!'-'}</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">工商注册号</td>
                    <td>${baseInfo.regNumber!'-'}</td>
                    <td class="one_name grey_td bolder_word">组织机构代码</td>
                    <td>${baseInfo.orgNumber!'-'}</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">统一信用代码</td>
                    <td>${baseInfo.taxNumber!'-'}</td>
                    <td class="one_name grey_td bolder_word">公司类型</td>
                    <td>${baseInfo.companyOrgType!'-'}</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">注册地址</td>
                    <td>${baseInfo.regLocation!'-'}</td>
                    <td class="one_name grey_td bolder_word">行业</td>
                    <td>${baseInfo.industry!'-'}</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">营业期限</td>
                    <td>-</td>
                    <td class="one_name grey_td bolder_word">核准日期</td>
                    <td>${baseInfo.approvedTime!'-'}</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">纳税人资质</td>
                    <td>-</td>
                    <td class="one_name grey_td bolder_word">人员规模</td>
                    <td>${baseInfo.staffNumRange!'-'}</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">实缴资本</td>
                    <td>${baseInfo.actualCapital!'-'}</td>
                    <td class="one_name grey_td bolder_word">登记机关</td>
                    <td>${baseInfo.regInstitute!'-'}</td>
                </tr>
                <tr class="dead_tr">
                    <td class="one_name grey_td bolder_word">经营范围</td>
                    <td colspan="3">${baseInfo.businessScope!'-'}</td>
                </tr>
            </table>
        </div>
        <!-- 登记信息结束 -->

        <!-- 企业基本信息以外列表项开始 -->
        <#if reportDatas??&&(reportDatas?size> 0) >
            <#list reportDatas?keys as key>
                <div class="force_paging">
                    <#if key !='企业基本信息'>
                        <h1 class="title_big">${key}</h1>
                    </#if>
                    <#list reportDatas[key] as item>
                        <!--小标题开始-->
                        <h1 class="title_small">${item.instanceData.tagName}</h1>

                        <!--小标题结束-->
                        <div class="surround_div">
                            <table class="green_table1">
                                <tr>
                                    <th width="8%">序号</th>
                                    <#list item.instanceData.instance.metadata?values as datas>
                                        <th class="col_title">${datas}</th>
                                    </#list>
                                </tr>

                                <#if item.instanceData.instance.data?? && (item.instanceData.instance.data?size> 0) >
                                    <#list item.instanceData.instance.data as listInfo>
                                        <tr class="dead_tr">
                                            <td>${listInfo_index+1}</td>
                                            <#list listInfo?values as value>
                                                <td>${value?html?default("--")}</td>
                                            </#list>
                                        </tr>
                                    </#list>
                                    <#else>
                                        <tr class="dead_tr">
                                            <td colspan="${item.instanceData.instance.metadata?size+1}">暂无数据</td>
                                        </tr>
                                </#if>
                            </table>
                        </div>
                        <!-- 判断是否分页 -->
<#--                        <#if item.instanceData.instance.data?size gt 25>-->
<#--                            <div class="t_text">-->
<#--                                <b>报告编号:</b><span>${reportNo}</span>-->
<#--                            </div>-->
<#--                        </#if>-->
                    </#list>
                </div>
            </#list>
        </#if>

        <!-- 企业基本信息以外列表项结束 -->

    </div>
    <!-- 页码 -->
    <!-- <div id="footer" style="">
    <span id="pagenumber" /> / <span id="pagecount" />
</div> -->
</body>

</html>
