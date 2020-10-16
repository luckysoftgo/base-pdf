<!DOCTYPE html
    PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>CR评级报告</title>
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
            border-bottom: 2px solid rgba(0, 0, 0, 0.1);
            ;
            position: relative;
        }

        .t_text::after {
            content: '';
            width: 100%;
            position: absolute;
            bottom: 3px;
            right: 0px;
            background: rgba(0, 0, 0, 0.4);
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

        .finance_abs {
            width: 100%;
            padding: 0 20px;
        }

        .finance_abs .desc {
            text-indent: 28px;
        }

        .abs_title {
            font-weight: 900;
            margin-bottom: 20px;
            border-bottom: 2px solid #438eff;
        }

        .cr_row {
            display: flex;
            align-items: flex-start;
            justify-content: center;
        }

        .cr_row>span {
            display: inline-block;
            width: 35%;
            margin: 0 2.5% 0 2.5%;
        }

        .cr_row .table_left,
        .cr_row .charts_right {
            width: 450px;
            min-height: 250px;
        }

        .gray_table {
            width: 100%;
            border-left: 0px solid #c4c5be;
            border-top: 0.5px solid #c4c5be;
            font-size: 15px;
        }

        .gray_table tr td {
            min-width: 70px;
            border-right: 0px;
            line-height: 35px;
        }

        .gray_table tr:first-child td {
            font-weight: 900;
            background: #b8dff3;
        }

        .gray_table tr:nth-child(odd) {
            background: #ddd;
        }

        .gray_table tr:nth-child(even) {
            background: #eee;
        }

        .gray_table tr td:first-child {
            max-width: 100px;
            text-align: left;
            padding-left: 15px;
        }

        .IdComInfo {
            text-indent: 45px;
            line-height: 28px;
        }

        .text {
            text-indent: 28px;
        }

        .margin_bottom {
            margin-bottom: 20px;
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
        <div style="background: url('file:///${imagePath}one-bown.png') bottom no-repeat;width:100%;">
            <div class="first-page" style="width:100%;">
                <div class="t_text"><b>报告编号:</b><span>${reportNo}</span></div>
                <!-- <div class="info"></div> -->
                <!-- <div style="background:#7C962D;float: left; font-size: 16px; padding:5px 15px; color:#ffffff;border-radius:8px;margin-top:320px;margin-left:200px;">${reportVerson}</div> -->
                <div class="title"
                    style="text-align: center;background: none;margin-top: 300px;font-weight: bolder;font-size: 32px;">
                    ${companyName}</div>
                <div class="title">评价报告</div>
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
            <div class="shuoming">
                <div class="shuoming_title"><strong>声明</strong></div>
                <div class="shuoming_content">
                    <p>一、我市地方金融工作局（以下简称“我金融局”）经${companyName}（以下简称“企业”）授权获取到其相关信息，并由我金融局运营的我市中小企业融资服务平台（以下简称“平台”）对企业出具信用信息报告。除因本次报告事项我金融局与报告所涉主体构成委托关系外，我金融局与其不存在任何影响监测行为独立、客观、公正的关联关系。
                    </p>
                    <p>二、本报告构成商业机密，未经我金融局及平台同意，任何机构或个人不得以任何形式将本报告全部或部分提供给第三人，不得发表、修改、歪曲、篡改、发行、展示、改编本报告全部或部分内容，不得以任何形式复制、转发或公开传播本报告全部或部分内容，不得将本报告全部或部分内容用于营利或未经允许的其他任何用途，我金融局及平台将保留采取一切法律手段追究其法律责任的权利。
                    </p>
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
                    <span class="catalog-title">1 企业背景</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">1.1 联系方式</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">1.2 工商信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">1.3 主要人员</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">2 出资人及出资信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">2.1 出资人及出资信息</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">3 对外投资信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">3.1 对外投资信息</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">4 风险信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.1 法律诉讼</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.2 行政处罚</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">4.3 股权出质</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">5 知识产权信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">5.1 商标信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">5.2 专利信息</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">6 经营信息</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">6.1 招投标</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">6.2 守信激励</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">6.3 资质证书</span>
                    <div class="line"></div>
                </li>
                <li class="child">
                    <span class="catalog-title">6.4 行政许可</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">7 信用等级</span>
                    <div class="line"></div>
                </li>
                <li>
                    <span class="catalog-title">附件</span>
                    <div class="line"></div>
                </li>
            </ul>
        </div>
        <!-- 财务概要开始 -->
        <div style="page-break-after: always;">
            <div class="finance_abs">
                <p class="desc">根据企业规模、企业背景、财务状况以及行业展望四个维度的综合评分，企业的信用等级为${crData.crLevel},基础信用额度为${crData.crAmount}万元。
                </p>
                <div class="cr_row">
                    <span class="table_left">
                        <p class="abs_title">信用评价</p>
                        <table class="gray_table">
                            <tr>
                                <td>主要指标 </td>
                                <td>评分</td>
                                <td>评价</td>
                            </tr>
                            <tr>
                                <td>企业规模：</td>
                                <td>${crData.a1}</td>
                                <td>${crData.a1LevelDesc}</td>
                            </tr>
                            <tr>
                                <td>企业背景：</td>
                                <td>${crData.a7}</td>
                                <td>${crData.a7LevelDesc}</td>
                            </tr>
                            <tr>
                                <td>财务状况：</td>
                                <td>${crData.a8}</td>
                                <td>${crData.a8LevelDesc}</td>
                            </tr>
                            <tr>
                                <td>行业展望：</td>
                                <td>${crData.a9}</td>
                                <td>${crData.a9LevelDesc}</td>
                            </tr>
                            <tr>
                                <td>信用等级：</td>
                                <td>${crData.crLevel}</td>
                                <td>${crData.crLevelDesc}</td>
                            </tr>
                            <tr>
                                <td>基础信用额度：</td>
                                <td colspan="2">
                                    ${crData.crAmount}万元
                                </td>
                            </tr>
                        </table>
                    </span>
                    <span class="charts_right">
                        <p class="abs_title">数据库企业信用风险分布</p>
                        <div id="CR" style="width: 350px;margin: 0 auto;">
                            <img src="file:///${crImage}" />
                        </div>
                    </span>
                </div>
                <div class="table_finance_abs surround_div force_paging">
                    <p class="abs_title">财务概要（%/元）</p>
                    <table class="green_table1">
                        <tr>
                            <th>财务数据/指标</th>
                            <th>${finInfo.lastTwo!'-'}年度</th>
                            <th>${finInfo.lastOne!'-'}年度</th>
                            <th>${finInfo.thisYear!'-'}年度</th>
                        </tr>
                        <tr>
                            <td>营业收入</td>
                            <td>${finInfo.lastTwoYysr!'-'}</td>
                            <td>${finInfo.lastOneYysr!'-'}</td>
                            <td>${finInfo.thisYearYysr!'-'}</td>
                        </tr>
                        <tr>
                            <td>利润总额</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td>净利润</td>
                            <td>${finInfo.lastTwoJlr!'-'}</td>
                            <td>${finInfo.lastOneJlr!'-'}</td>
                            <td>${finInfo.thisYearJlr!'-'}</td>
                        </tr>
                        <tr>
                            <td>资产总额</td>
                            <td>${finInfo.lastTwoZcze!'-'}</td>
                            <td>${finInfo.lastOneZcze!'-'}</td>
                            <td>${finInfo.thisYearZcze!'-'}</td>
                        </tr>
                        <tr>
                            <td>负债合计</td>
                            <td>${finInfo.lastTwoFzhj!'-'}</td>
                            <td>${finInfo.lastOneFzhj!'-'}</td>
                            <td>${finInfo.thisYearFzhj!'-'}</td>
                        </tr>
                        <tr>
                            <td>所有者权益合计</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td>净资产收益率</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td>销售（营业）利润率</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td>总资产周转次数</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td>资产负债率</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td>销售（营业）增长率</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td>总资产增长率</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <!-- 财务概要结束 -->

        <!-- 企业背景 -->
        <!-- 栏目大标题开始 -->
        <h1 class="title_big">企业背景</h1>
        <!-- 栏目大标题结束 -->

        <!-- 小标题开始 -->
        <h1 class="title_small">联系方式</h1>
        <!-- 小标题结束 -->

        <!-- 企业基本信息开始 -->
        <div class="surround_div">
            <table class="green_table1">
                <tr class="dead_tr">
                    <th class="one_name grey_td bolder_word">序号</th>
                    <th class="bolder_word">电话</th>
                    <th class="bolder_word">邮箱</th>
                    <th class="bolder_word">地址</th>
                    <th class="bolder_word">网址</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>${baseInfo.LXDH!'-'}</td>
                    <td>${baseInfo.EMAIL!'-'}</td>
                    <td>${baseInfo.regLocation!'-'}</td>
                    <td>${baseInfo.QYWZ!'-'}</td>
                </tr>
            </table>
        </div>

        <!-- 小标题开始 -->
        <h1 class="title_small">工商信息</h1>
        <!-- 小标题结束 -->
        <div class="surround_div">
            <p class="IdComInfo"><strong>企业名称：</strong>${baseInfo.companyName}</p>
            <p class="IdComInfo"><strong>统一社会信用代码：</strong>${baseInfo.taxNumber!'-'}</p>
            <p class="IdComInfo"><strong>法定代表人：</strong>${baseInfo.legalPersonName!'-'}</p>
            <p class="IdComInfo"><strong>企业类型：</strong>${baseInfo.companyOrgType!'-'}</p>
            <p class="IdComInfo"><strong>所属行业：</strong>${baseInfo.industry!'-'}</p>
            <p class="IdComInfo"><strong>经营状态：</strong>${baseInfo.regStatus!'-'}</p>
            <p class="IdComInfo"><strong>注册资本：</strong>${baseInfo.regCapital!'-'}</p>
            <p class="IdComInfo"><strong>注册时间：</strong>${baseInfo.estiblishTime!'-'}</p>
            <p class="IdComInfo"><strong>注册地址：</strong>${baseInfo.regLocation!'-'}</p>
            <p class="IdComInfo"><strong>营业期限：</strong>${baseInfo.businessTerm!'-'}</p>
            <p class="IdComInfo"><strong>经营范围：</strong>${baseInfo.businessScope!'-'}</p>
            <p class="IdComInfo"><strong>登记机关：</strong>${baseInfo.regInstitute!'-'}</p>
            <p class="IdComInfo"><strong>核准日期：</strong>${baseInfo.approvedTime!'-'}</p>
        </div>

        <!-- 小标题开始 -->
        <h1 class="title_small">主要人员</h1>
        <!-- 小标题结束 -->
        <table class="green_table1">
            <tr class="dead_tr">
                <th class="one_name grey_td bolder_word">序号</th>
                <th class="bolder_word">姓名</th>
                <th class="bolder_word">职位</th>
            </tr>
            <tr>
                <td>1</td>
                <td>-</td>
                <td>-</td>
            </tr>
        </table>
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
                        <#if item.instanceData.instance.data?size gt 25>
                            <div>
                                <img src="file:///${imagePath}contenginfo_01.jpg"
                                    style="width: 100%;height: 60px;padding: 0;margin:0;"></img>
                            </div>
                        </#if>
                    </#list>
                </div>
            </#list>
        </#if>
        <!-- 企业基本信息以外列表项结束 -->

        <!-- 因为没有财务数据，财务指标暂时隐藏 -->
        <!-- 财务信息列表项开始 -->
<#--        <div class="force_paging">-->
<#--            <h1 class="title_big">财务信息</h1>-->
<#--            <!-- 小标题开始 &ndash;&gt;-->
<#--            <h1 class="title_small">财务数据</h1>-->
<#--            <!-- 小标题结束 &ndash;&gt;-->
<#--            <div class="surround_div margin_bottom">-->
<#--                <p class="text">资产负债表（万元）</p>-->
<#--                <table class="green_table1">-->
<#--                    <tr>-->
<#--                        <th>财务数据</th>-->
<#--                        <th>2017年度</th>-->
<#--                        <th>2018年度</th>-->
<#--                        <th>2019年度</th>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>资产总额</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>负债合计</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>所有者权益合计</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>负债及所有者权益合计</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                </table>-->
<#--            </div>-->
<#--            <div class="surround_div margin_bottom">-->
<#--                <p class="text">利润表（万元）</p>-->
<#--                <table class="green_table1">-->
<#--                    <tr>-->
<#--                        <th>财务数据</th>-->
<#--                        <th>2017年度</th>-->
<#--                        <th>2018年度</th>-->
<#--                        <th>2019年度</th>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>营业收入</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>利润总额</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>净利润</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                </table>-->
<#--            </div>-->
<#--            <div class="surround_div margin_bottom">-->
<#--                <p class="text">重要财务数据变化率（万元）</p>-->
<#--                <table class="green_table1">-->
<#--                    <tr>-->
<#--                        <th>财务数据</th>-->
<#--                        <th>2017年度</th>-->
<#--                        <th>2018年度</th>-->
<#--                        <th>2019年度</th>-->
<#--                        <th>复合增长率</th>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>营业收入</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>净利润</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>资产总额</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>负债合计</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>所有者权益合计</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                </table>-->
<#--            </div>-->

<#--            <!-- 小标题开始 &ndash;&gt;-->
<#--            <h1 class="title_small">财务指标</h1>-->
<#--            <!-- 小标题结束 &ndash;&gt;-->
<#--            <div class="surround_div margin_bottom">-->
<#--                <p class="text">重要比率表（%/次）</p>-->
<#--                <table class="green_table1">-->
<#--                    <tr>-->
<#--                        <th>盈利能力财务指标</th>-->
<#--                        <th>2017年度</th>-->
<#--                        <th>2018年度</th>-->
<#--                        <th>2019年度</th>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>毛利率</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>净资产收益率</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>总资产报酬率</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>销售（营业）利润率</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                </table>-->
<#--            </div>-->
<#--            <div class="surround_div margin_bottom">-->
<#--                <table class="green_table1">-->
<#--                    <tr>-->
<#--                        <th>营运能力财务指标</th>-->
<#--                        <th>2017年度</th>-->
<#--                        <th>2018年度</th>-->
<#--                        <th>2019年度</th>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>总资产周转次数</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>应收账款周转次数</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                </table>-->
<#--            </div>-->
<#--            <div class="surround_div margin_bottom">-->
<#--                <table class="green_table1">-->
<#--                    <tr>-->
<#--                        <th>偿债能力财务指标</th>-->
<#--                        <th>2017年度</th>-->
<#--                        <th>2018年度</th>-->
<#--                        <th>2019年度</th>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>资产负债率</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                </table>-->
<#--            </div>-->
<#--            <div class="surround_div margin_bottom">-->
<#--                <table class="green_table1">-->
<#--                    <tr>-->
<#--                        <th>发展能力财务指标</th>-->
<#--                        <th>2017年度</th>-->
<#--                        <th>2018年度</th>-->
<#--                        <th>2019年度</th>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>销售（营业）增长率</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                    <tr>-->
<#--                        <td>总资产增长率</td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                        <td></td>-->
<#--                    </tr>-->
<#--                </table>-->
<#--            </div>-->
<#--        </div>-->
        <!-- 财务信息列表项结束 -->

        <!-- 信用等级开始 -->
        <div class="force_paging">
            <h1 class="title_big">信用等级</h1>
            <h3>CR等级：${crData.crLevel}</h3>
            <p class="text">在分析目标公司信用等级时，综合考虑目标公司的规模、背景与历史，对于行业平均水平的财务状况和经营情况，目标公司的信用历史等。信用等级的含义如下：</p>
            <table class="green_table1">
                <tr>
                    <th>等级</th>
                    <th>风险水平</th>
                    <th>建议</th>
                </tr>
                <tr>
                    <td>CR1</td>
                    <td>风险极小</td>
                    <td>信贷交易可以很宽松</td>
                </tr>
                <tr>
                    <td>CR2</td>
                    <td>风险小</td>
                    <td>信贷交易可以较宽松</td>
                </tr>
                <tr>
                    <td>CR3</td>
                    <td>风险低于平均水平</td>
                    <td>可以正常信贷条件与其交易</td>
                </tr>
                <tr>
                    <td>CR4</td>
                    <td>风险属于平均水平</td>
                    <td>可在密切监控基础上以正常信贷条件与其交易</td>
                </tr>
                <tr>
                    <td>CR5</td>
                    <td>风险高于平均水平</td>
                    <td>尽量避免信贷交易</td>
                </tr>
                <tr>
                    <td>CR6</td>
                    <td>风险大</td>
                    <td>信贷交易应以担保为基础</td>
                </tr>
                <tr>
                    <td>CR7</td>
                    <td>风险很大</td>
                    <td>只在现金基础上与其交易</td>
                </tr>
            </table>
            <h3>基础信用额度:${crData.crAmount}万元</h3>
            <p class="text">
                在计算基础信用额度时假定目标公司平均从多家供应商采购主要商品或服务，主要计算因素包括净资产、总资产、营业收入、利润和信用风险等级等，没有考虑您与目标公司的具体交易情况。您在进行信贷决策时可参考以下建议但需根据您的营销策略和信用政策做出适当调整。
            </p>
            <table class="green_table1">
                <tr>
                    <th>年供货量占目标公司营业收入的比例</th>
                    <th>建议信用额度</th>
                </tr>
                <tr>
                    <td>40%以上</td>
                    <td>基础信用额度的4倍以上</td>
                </tr>
                <tr>
                    <td>30%～40%</td>
                    <td>基础信用额度的3~4倍</td>
                </tr>
                <tr>
                    <td>20%～30%</td>
                    <td>基础信用额度的2~3倍</td>
                </tr>
                <tr>
                    <td>10%～20%</td>
                    <td>基础信用额度的1~2倍</td>
                </tr>
                <tr>
                    <td>10%以下</td>
                    <td>基础信用额度以内</td>
                </tr>
            </table>
        </div>
        <!-- 信用等级结束 -->

        <!-- 附件开始 -->
        <div class="force_paging">
            <h3>附件：</h3>
            <table class="gray_table">
                <tr>
                    <td>财务指标计算公式：</td>
                </tr>
                <tr>
                    <td>毛利率=（主营业务收入-主营业务成本）/主营业务收入×100%</td>
                </tr>
                <tr>
                    <td>净资产收益率=净利润/净资产×100%</td>
                </tr>
                <tr>
                    <td>总资产报酬率=息税前利润/资产平均总额×100%</td>
                </tr>
                <tr>
                    <td>销售（营业）利润率=销售（营业）利润/营业总收入×100%</td>
                </tr>
                <tr>
                    <td>总资产周转次数=营业总收入/平均资产总额</td>
                </tr>
                <tr>
                    <td>应收账款周转次数=营业总收入/应收账款平均余额</td>
                </tr>
                <tr>
                    <td>资产负债率=负债总额/资产总额×100%</td>
                </tr>
                <tr>
                    <td>销售（营业）增长率=本年营业总收入增长额/上年营业总收入×100%</td>
                </tr>
                <tr>
                    <td>总资产增长率=（年末资产总额-年初资产总额）/年初资产总额×100%</td>
                </tr>
            </table>
        </div>
        <!-- 附件结束 -->
    </div>
    <!-- 页码 -->
    <!-- <div id="footer" style="">
    <span id="pagenumber" /> / <span id="pagecount" />
</div> -->
</body>
<script type="text/javascript">
    var locat = document.getElementsByClassName('locat')[0];
    locat.className = '${crResult}';
</script>

</html>
