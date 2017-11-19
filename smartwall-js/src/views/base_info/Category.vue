<style scoped lang="less">
    .category {

    }
</style>

<template>
    <div class="category">
        <table  class="editor">
            <tr class="top">
                <td class="title">试题类别</td>
            </tr>
            <tr class="middle">
                <td class="items">
                   <div v-for="item in items" class="item" :class="{'item-select':item.select}" @click="click_item(item)">{{item.name}}<a title="删除" class="xui-icon fa fa-minus" @click.stop="del_item(item)"></a></div> 
                </td>
            </tr>
            <tr class="bottom">
                <td>
                    <div class="tools">
                        <div class="input-wrapper">
                            <input v-model="newName" placeholder="新增类别"></input>
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
        name: 'Category',
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
                if(!this.kindGuid) {
                    alert("请选择试题分类");
                    return;
                }

                if (!this.newName) {
                    alert("请输入类别名称");
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
            click_item(item) {
                data.updateSelected(this.items, false);
                item.select = true;

                eventBus.$emit("category-select", item.guid);
            },
            del_item(item) {
                if (confirm('确认删除"' + item.name + '"?"')) {
                    this.del(item);
                }
            },
            load_data(kind) {
                var that = this;

                this.kindGuid = kind;
                data.getCategoris(kind).then(function(req) {
                    data.updateSelected(req, false);
                    that.items = req;

                    let guid = '';
                    if (that.items.length > 0) {
                        let item = that.items[0];
                        item.select = true;
                        guid = item.guid;
                    }

                    eventBus.$emit("category-select", guid);
                }).catch(function(error) {
                    console.log(error)
                });
            },
            add(name) {
                var that = this;
                data.saveCategory(name, this.kindGuid).then(function(req) {
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

                data.delCategory(item.guid).then(function(req) {
                    that.items.splice(that.items.indexOf(item), 1);
                });
            }
        },
        created() {
            eventBus.$on("kind-select", this.load_data);
        },
        beforeDestroy() {
            eventBus.$off("kind-select");
        }
    };
</script>
