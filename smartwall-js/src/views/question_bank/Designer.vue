<style lang="less">
    @import './Designer.less';

    .designer {
        width: 100%;
        height: 100%;
        border: 0;
        padding: 0;
        margin: 0;
        border-collapse: collapse;
    }

    #question-list {
        width: 300px;
        float: left;

        table {
            width: 100%;
            border-collapse: collapse;

            td, th {
                height: 28px;
                font-size: 12px;
            }

            th {
                background: #f9f9f9;
            }

            tr:hover, .item-select {
                background: #f9f9f9;
                cursor: pointer;
            }
        }
    }

    #question-editor {
        margin-left: 301px;
        margin-right: 301px;
        border-left: 1px solid #dddee1;
        padding-left: 10px;

        #tools, #type-row {
            padding: 10px;
            border-bottom: 1px dashed #dddee1;
        }

        #type {
            font-size: 22px;
            font-weight: bold;
            margin-left: 10px;
            color: #2b85e4;
        }
    }

    #question-helper {
        float: right;
        width: 300px;
    }

    #stem {
        width: 100%;
        margin-top: 10px;
        border-bottom: 1px dashed #dddee1;
        margin-bottom: 10px;
    }

    #editor {
        >div {
            padding-left: 10px;
        }
        
        textarea {
            width: 100%;
            height: 80px;
        }

        #options {
            border-bottom: 1px dashed #dddee1;
        }

        #options .no {
            font-weight: bold;
            float: left;
            line-height: 32px;
        }

        #options >div {
            padding: 5px;
        }

        #options .opt {
            margin-left:25px;

            input {
                width: 100%;
            }
        }

        #answer, #degree {
            padding: 10px;
            border-bottom: 1px dashed #dddee1;
        }

        #analysis, #outline, #test {
        	border-bottom: 1px dashed #dddee1;
        }
    }
</style>

<template>
    <div class="designer">
        <ChapterSelector @on-select-chapter='onSelectChapter'></ChapterSelector>
        <div>
            <div id="question-list">
                <table>
                    <thead>
                        <tr>
                            <th style="width:40px;">序列</th>
                            <th style="width:40px;">类型</th>
                            <th>内容</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in items" :class="{'item-select':item.select}" @click="selectQuestion(index,item)">
                            <td style="text-align:center">{{index + 1}}</td>
                            <td style="text-align:center">{{item.type}}</td>
                            <td>{{item.text}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div id="question-helper">
                <span></span>
            </div>
            <div id="question-editor">
                <div id="tools">
                    <span>添加试题：</span>
                    <button @click="addA1">A1</button><!--A1 型题（单句型最佳选择题）：每一道试题下面有A、B、C、D、E五个备选答案，请从中选择一个最佳答案，并在答题卡上将相应题号的相应字母所属的方框涂黑。-->
                    <button @click="addA2">A2</button><!--A2 型题（病例摘要型最佳选择题）：每一道试题是以一个小案例出现的，其下面都有A、B、C、D、E五个备选答案，请从中选择一个最佳答案，并在答题卡上将相应题号的相应字母所属的方框涂黑。-->
                    <button @click="addB1">B1</button><!--B1 型题（标准配伍题）：提供若干组试题，每组试题共用在试题前列出的A、B、C、D、E五个备选答案，从中选择一个与问题关系最密切的答案，并在答题卡上将相应题号的相应字母所属的方框涂黑。某个备选答案可能被选择一次、多次或不被选择。-->
                    <button @click="addA3">A3</button><!--A3 型题（病例组型最佳选择题）：提供若干个案例，每个案例下设若干道试题。根据案例所提供的信息，在每一道试题下面的A、B、C、D、E五个备选答案中选择一个最佳答案，并在答题卡上将相应题号的相应字母所属的方框涂黑。-->
                    <button @click="addA4">A4</button><!--A4 型题（病例串型最佳选择题）：以下提供若干个案例，每个案例下设若干道试题。请根据案例所提供的信息，在每一道试题下面的A、B、C、D、E五个备选答案中选择一个最佳答案，并在答题卡上将相应题号的相应字母所属的方框涂黑。有的试题中提供了与病例相关的辅助或假定信息，要根据该题提供的信息来回答问题，这些信息不一定与病例中的具体病人有关。-->

                    <button style="margin-left:40px" @click="saveQuestions">保存</button>
                </div>
                <div id="type-row">
                    <span>试题类型</span>
                    <span id="type">{{current.type}}</span>
                </div>
                <div id="editor">
                    <div id="stem">
                        <span>题干:</span>
                        <br/>
                        <textarea v-model="current.text"></textarea>
                    </div>
                    <div id="options">
                        <div>选项:</div>
                        <div><span class="no">A.</span><div class="opt"><input v-model="current.opt_A"></input></div></div>
                        <div><span class="no">B.</span><div class="opt"><input v-model="current.opt_B"></input></div></div>
                        <div><span class="no">E.</span><div class="opt"><input v-model="current.opt_C"></input></div></div>
                        <div><span class="no">D.</span><div class="opt"><input v-model="current.opt_D"></input></div></div>
                        <div><span class="no">E.</span><div class="opt"><input v-model="current.opt_E"></input></div></div>
                    </div>
                    <div id="degree">
                    	<span>难度</span>
                    	<select v-model="current.degree">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                    <div id="answer">
                        <span>答案:</span>
                        <select v-model="current.answer">
                            <option value="A">A</option>
                            <option value="B">B</option>
                            <option value="C">C</option>
                            <option value="D">D</option>
                            <option value="E">E</option>
                        </select>
                    </div>
                    <div id="outline">
                        <span>大纲考点:</span>
                        <br/>
                        <textarea v-model="current.outline"></textarea>
                    </div>
                    <div id="test">
                        <span>考点还原:</span>
                        <br/>
                        <textarea v-model="current.test"></textarea>
                    </div>
                    <div id="analysis">
                        <span>答案解析:</span>
                        <br/>
                        <textarea v-model="current.analysis"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>    
</template>

<script>
    import ChapterSelector from './ChapterSelector';
    import data from '../../js/data';

    export default {
        name: 'BaseInfo',
        data() {
            return {
                items: [],
                index: 0,
                chapterGuid: '',
                categoryGuid: '',
                subjectGuid: '',
                current: {
                    answer: 'A'
                }
            };
        },
        props: {},
        computed: {},
        components: {
            ChapterSelector
        },
        methods: {
            onSelectChapter(select) {
            	this.categoryGuid = select[0];
            	this.subjectGuid = select[1];
                this.loadQuestions(select[2]);
            },

            /**
             * 根据chapter guid加载试题
             */
            loadQuestions(guid) {
                var that = this;
                this.chapterGuid = guid;
                this.items = [];
                data.getChapterQuestions(this.categoryGuid, this.subjectGuid, this.chapterGuid).then(function(req) {
                    for(let o of req){
                    	let item = JSON.parse(o.data);
                    	item.guid = o.guid;
                    	item.select = false;

                    	that.items.push(item);
                    }

                    if (that.items.length > 0) {
                        that.current = that.items[0];
                        that.current.select = true;
                    }else {
                        that.current = {}
                    }                   
                });
            },

            saveQuestions() {
            	var that = this;
                data.saveChapterQuestions(this.index, this.current.guid||'0', this.categoryGuid, this.subjectGuid, this.chapterGuid, JSON.stringify(this.current)).then(function(req) {
                    	that.current.guid = req;
                });
            },

            addQuestion(type) {
                this.current = {
                    type: type,
                    text: '新题目',
                    opts: {}
                };

                this.items.push(this.current);
                this.index = this.items.length;
                this.saveQuestions();
            },

            addA1() {
                this.addQuestion('A1');
            },

            addA2() {
                this.addQuestion('A2');
            },

            addB1() {
                this.addQuestion('B1');
            },

            addA3() {
                this.addQuestion('A3');
            },

            addA4() {
                this.addQuestion('A4');
            },

            selectQuestion(index, item) {
                if(this.current != item) {
                    this.current.select = false;
                }
                this.index = (index + 1);
                this.current = item;
                this.current.select = true;
            }
        },
        created() {}
    };
</script>
