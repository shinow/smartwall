<style scoped lang="less">
    .chapter {
        
    }
</style>

<template>
    <div class="chapter">
        <table  class="editor">
            <tr class="top">
                <td class="title">试题章节</td>
            </tr>
            <tr class="middle">
                <td class="items">
                   <div v-for="item in items" class="item">{{item.name}}<a title="删除" class="xui-icon fa fa-minus" @click.stop="del_item(item)"></a></div> 
                </td>
            </tr>
            <tr class="bottom">
                <td>
                    <div class="tools">
                        <div class="input-wrapper">
                            <input v-model="newName"  placeholder="新增章节"></input>
                        </div>
                        <button @click="add_item"><i class="xui-icon fa fa-plus"></i></button>
                    </div>
                </td>
            </tr>
        </table>
    </div>        
</template>

<script>
    import eventBus from '../../js/event_bus';
    import data from '../../js/data';

    export default {
        name: 'Chapter',
        data() {
            return {
                items: null,
                newName: null
            };
        },
        props: {},
        computed: {},
        methods: {
            add_item() {
                if(!this.subjectGuid) {
                    alert("请选择试题科目");
                    return;
                }

                if (!this.newName) {
                    alert("请输入章节名称");
                    return;
                }

                for (let item of this.items) {
                    if (this.newName == item.name) {
                        alert(this.newName + ' 已经存在！');
                        return;
                    }
                }

                this.add(this.newName);
                this.newName = '';
            },

            del_item(item) {
                if (confirm('确认删除"' + item.name + '"?"')) {
                    this.del(item);
                }
            },
            load_data(subject) {
                var that = this;

                this.subjectGuid = subject;
                data.getChapters(subject).then(function(req) {
                    that.items = req;
                }).catch(function(error) {
                    console.log(error)
                });
            },
            add(name) {
                var that = this;
                data.saveChapter(name, this.subjectGuid).then(function(req) {
                    let item = {
                        name: name,
                        guid: req,
                        select: false
                    };

                    that.items.push(item);
                });
            },

            del(item) {
                var that = this;

                data.delChapter(item.guid).then(function(req) {
                    that.items.splice(that.items.indexOf(item), 1);
                });
            }
        },
        created() {
            eventBus.$on("subject-select", this.load_data);
        },
        beforeDestroy() {
            eventBus.$off("subject-select");
        }
    };
</script>
