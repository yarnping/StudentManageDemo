<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet" />
    <link href="http://cdn.bootcss.com/bootstrap-daterangepicker/2.1.24/daterangepicker.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/v/bs/jqc-1.12.3/dt-1.10.12/datatables.min.css" rel="stylesheet">
    <style type="text/css">
        body {
            background-color: #fff;
        }
        #container {

        }
        #header-filter {
            background-color: #f7f7f9;
            border: 1px solid #e1e1e8;
        }
        #content {
            margin-top: 20px;
        }
        #student-list {
        }
    </style>

    <meta charset="UTF-8">
    <title>student manage</title>
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script src="http://cdn.bootcss.com/bootstrap-daterangepicker/2.1.24/moment.min.js"></script>
    <script src="https://cdn.datatables.net/v/bs/jqc-1.12.3/dt-1.10.12/datatables.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap-daterangepicker/2.1.24/daterangepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>

</head>
<body>

    <div class="container">
        <div id="header-filter">
            <div style="width: 400px; padding: 25px 15px 15px;">
                <form >
                    <div class="form-group">
                        <label for="code">编码</label>
                        <input type="text" class="form-control" id="code" placeholder="编码">
                    </div>
                    <div class="form-group">
                        <label for="name">名称</label>
                        <input type="text" class="form-control" id="name" placeholder="小李">
                    </div>
                    <div class="form-group">
                        <label for="name">账号类型</label>
                        <br />
                        <select class="js-example-basic-single" style="width: 120px;">
                            <option value="-1" selected="selected">所有</option>
                            <option value="1">role1</option>
                            <option value="2">role2</option>
                            <option value="3">role3</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">注册时间</label>
                        <input type="text" class="form-control" name="daterange" value="01/01/2015 1:30 PM - 01/01/2015 2:00 PM" />
                    </div>
                    <button id="btnfilter" type="button" class="btn btn-primary" onclick="javascript:startFilter()">过滤</button>
                </form>
            </div>
        </div>
        <div id="content">
            <table id="student-list" class="table table-striped table-bordered" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>编码</th>
                    <th>名称</th>
                    <th>备注</th>
                    <th>状态</th>
                    <th>类型</th>
                    <th>创建时间</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <!-- dialog -->
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
            Launch demo modal
        </button>
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                    </div>
                    <div class="modal-body">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
    </div>


</body>
<script type="text/javascript">
    //开始时间
    var filterStartDateTime = 0
    //结束时间
    var filterEndDateTime = 0
    //账号类型
    var filterRole = -1

    //select2
    $(".js-example-basic-single").on("select2:select", function(e) {
        filterRole = $(this).find(":selected").attr("value");
    });



    //date range picker
    $(function() {
        $('input[name="daterange"]').daterangepicker({
            timePicker: true,
            timePicker24Hour: true,
            timePickerIncrement: 30,
            locale: {
                format: 'YYYY年MM月DD日 h:mm A'
            },
            startDate: '2016年01月01日',
            endDate: '2016年-12月-31日'
        });
    });

    $('input[name="daterange"]').on('apply.daterangepicker', function(ev, picker) {
        filterStartDateTime = picker.startDate.valueOf()/1000
        filterEndDateTime   = picker.endDate.valueOf()/1000
    });

    //table相关
    var table;
    $(document).ready(function(){
        $(".js-example-basic-single").select2({
            placeholder: 'xuanze'
        });
        table = $('#student-list').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": 'jsontest',
            "lengthMenu": [[10, 30, 50, -1], [10, 30, 50, "全部"]]
        });


//        $('#student-list tbody')
//                .on( 'mouseenter', 'td', function () {
//                    var colIdx = table.cell(this).index().row;
//                    $( table.cells().nodes() ).removeClass( 'highlight' );
//                    $( table.row( colIdx ).nodes() ).addClass( 'highlight' );
//                } );
//
//        table.on('select', function(e,dt,type,indexes){
//            alert("select")
//            if (type  == 'rows') {
//                var data = table.rows(indexes).data.pluck('id');
//                alert(data)
//                alert("rows");
//            }
//        })
    });

    //选择单行之后
    $('#student-list').on( 'click', 'tr', function () {
        var d = table.row( this ).data();

    } );

    $('#student-list').on( 'length.dt', function ( e, settings, len ) {
        console.log( 'New page length: '+len );
    } );
    $('#student-list').on( 'page.dt', function () {
        var info = table.page.info();
        $('#test').html( 'Showing page: '+(info.page+1)+' of '+info.pages );
    } );


    // 条件过滤
    var startFilter = function(){
        if($("#btnfilter").text() == "过滤") {
            var code = $("#code").val();
            var name = $("#name").val();
            var filterUrl = "stufilter?code=" + code + "&name=" + name + "&role=" + filterRole + "&startTime=" + filterStartDateTime
                    + "&endTime=" + filterEndDateTime;
            table.ajax.url( filterUrl ).load();
            $("#btnfilter").addClass("active").text("取消过滤");
//            $.ajax({
//                type: 'GET',
//                url : 'stufilter',
//                data : {"name":'dfd'},
//                success: function(data){
//                    table.ajax.url( 'stufilter' ).load();
//                }
//            })
        }else{
            $("#btnfilter").removeClass("active").text("过滤");
            table.ajax.url("jsontest").load( null, true)
            table.draw();
        }

    }

</script>
</html>