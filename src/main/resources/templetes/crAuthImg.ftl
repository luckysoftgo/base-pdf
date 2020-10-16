<!DOCTYPE html
    PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>CR评级结果</title>
    <style type="text/css">
        .model_pic {
            width: 300px;
            height: 125px;
            margin: 0 auto;
        }

        .model_pic .box {
            position: relative;
            height: 100%;
        }

        .model_pic .box .bar {
            position: absolute;
            bottom: 30%;
            width: 100%;
            height: 12px;
        }

        .model_pic .box .bar>span {
            display: inline-block;
            height: 12px;
        }

        .model_pic .box .bar .b_cr1 {
            position: absolute;
            left: 0;
            bottom: 0;
            background: #b4351f;
            /*width: calc(300px * 0.03);*/
            width: 9px;
        }

        .model_pic .box .bar .b_cr2 {
            position: absolute;
            background: #dd543c;
            bottom: 0;
            left: 3%;
            /* width: calc(300px * 0.07);*/
            width: 21px;
        }

        .model_pic .box .bar .b_cr3 {
            position: absolute;
            background: #e36f59;
            bottom: 0;
            left: 10%;
            /*width: calc(300px * 0.16);*/
            width: 48px
        }

        .model_pic .box .bar .b_cr4 {
            position: absolute;
            background: #ea8f80;
            bottom: 0;
            left: 26%;
            /* width: calc(300px * 0.4);*/
            width: 120px
        }

        .model_pic .box .bar .b_cr5 {
            position: absolute;
            background: #efaa9e;
            bottom: 0;
            left: 66%;
            /*width: calc(300px * 0.2);*/
            width: 60px
        }

        .model_pic .box .bar .b_cr6 {
            position: absolute;
            background: #f4c9c1;
            bottom: 0;
            left: 86%;
            /*width: calc(300px * 0.07);*/
            width: 21px
        }

        .model_pic .box .bar .b_cr7 {
            position: absolute;
            background: #fae2de;
            bottom: 0;
            left: 93%;
            /*width: calc(300px * 0.07);*/
            width: 21px
        }

        .model_pic .box .percent {
            position: absolute;
            width: 100%;
            height: 100%;
            z-index: 2;
        }

        .model_pic .box .percent>span {
            display: inline-block;
            position: absolute;
            bottom: 0;
            height: 70%;
        }

        .model_pic .box .percent>span>span {
            font-size: 13px;
            font-weight: bold;
            color: #444;
            position: absolute;
            top: 0;
            left: -25px;
            display: inline-block;
            text-align: center;
            width: 50px;
        }

        .model_pic .box .percent>span i {
            border-right: 0.5px solid #333333cc;
            position: absolute;
            bottom: 15px;
            left: -1px;
            height: 60%;
            width: 1px;
        }

        .model_pic .box .percent>span>em {
            font-size: 12px;
            font-weight: bold;
            font-style: normal;
            color: #d43e24;
            position: absolute;
            bottom: 0;
        }

        .model_pic .box .percent .p_cr1 {
            height: 80%;
            left: 3%;
        }

        .model_pic .box .percent .p_cr2 {
            height: 90%;
            left: 10%;
        }

        .model_pic .box .percent .p_cr3 {
            height: 90%;
            left: 26%;
        }

        .model_pic .box .percent .p_cr4 {
            height: 90%;
            left: 66%;
        }

        .model_pic .box .percent .p_cr5 {
            height: 80%;
            left: 86%;
        }

        .model_pic .box .percent .p_cr6 {
            height: 90%;
            left: 93%;
        }

        .model_pic .box .percent .p_cr7 {
            height: 100%;
            left: 100%;
        }

        .model_pic .box .locat {
            position: absolute;
            bottom: 40%;
            text-align: center;
            font-size: 14px;
            font-weight: bold;
            color: #d23e24;
            line-height: 16px;
            z-index: 3;
            background: #ffffffcc;
        }

        .model_pic .box .locat p {
            margin-bottom: 10px;
        }

        /* 定位数据 */
        .model_pic .box .locat i {
            border-top: 7px solid #d23e24;
            border-left: 5px solid transparent;
            border-right: 5px solid transparent;
            border-bottom: 0px;
            display: inline-block;
        }

        .model_pic .box .locat_1 {
            left: calc(300px * 0.03 - 28px);
        }

        .model_pic .box .locat_2 {
            left: calc(300px * 0.1 - 28px);
        }

        .model_pic .box .locat_3 {
            left: calc(300px * 0.26 - 28px);
        }

        .model_pic .box .locat_4 {
            left: calc(300px * 0.66 - 28px);
        }

        .model_pic .box .locat_5 {
            left: calc(300px * 0.86 - 28px);
        }

        .model_pic .box .locat_6 {
            left: calc(300px * 0.93 - 28px);
        }

        .model_pic .box .locat_7 {
            left: calc(300px - 28px);
        }
    </style>
</head>

<body style="font-family: SimSun; margin: 0px; padding: 0px;">
    <div class="page" style="margin: 0px; padding: 0px;">
        
            <div id="CR">
                <div class="model_pic">
                    <div class="box">
                        <div class="bar">
                            <span class="b_cr1"></span>
                            <span class="b_cr2"></span>
                            <span class="b_cr3"></span>
                            <span class="b_cr4"></span>
                            <span class="b_cr5"></span>
                            <span class="b_cr6"></span>
                            <span class="b_cr7"></span>
                        </div>
                        <div class="percent">
                            <span class="p_cr1">
                                <span>0.16%</span>
                                <i></i>
                                <em>CR1</em>
                            </span>
                            <span class="p_cr2">
                                <span>4.7%</span>
                                <i></i>
                                <em>CR2</em>
                            </span>
                            <span class="p_cr3">
                                <span>21.98%</span>
                                <i></i>
                                <em>CR3</em>
                            </span>
                            <span class="p_cr4">
                                <span>71.22%</span>
                                <i></i>
                                <em>CR4</em>
                            </span>
                            <span class="p_cr5">
                                <span>91.54%</span>
                                <i></i>
                                <em>CR5</em>
                            </span>
                            <span class="p_cr6">
                                <span>94.89%</span>
                                <i></i>
                                <em>CR6</em>
                            </span>
                            <span class="p_cr7">
                                <span>100%</span>
                                <i></i>
                                <em>CR7</em>
                            </span>
                        </div>
                        <div class="locat">
                            <p>我的企业</p>
                            <i></i>
                        </div>
                    </div>
                </div>
            </div>
 
    </div>
</body>
<script type="text/javascript">
    var locat = document.getElementsByClassName('locat')[0];
    locat.className = '${crResult}';
</script>

</html>