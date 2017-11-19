<style scoped lang="less">
    .kind {
        
    }
</style>

<template>
    <div class="kind">
        <table  class="editor">
            <tr class="top">
                <td class="title">试题分类</td>
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
                            <input v-model="newName" placeholder="新增分类"></input>
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
        name: 'Kind',
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
                if (!this.newName) {
                    alert("请输入分类名称");
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

                eventBus.$emit("kind-select", item.guid);
            },
            del_item(item) {
                if (confirm('确认删除"' + item.name + '"?"')) {
                    this.del(item);
                }
            },

            load_data() {
                var that = this;
                data.getKinds().then(function(req) {
                    data.updateSelected(req, false);

                    that.items = req;
                }).catch(function(error) {
                    console.log(error)
                });
            },

            add(name) {
                var that = this;
                data.saveKind(name).then(function(req) {
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

                data.delKind(item.guid).then(function(req){
                    that.items.splice(that.items.indexOf(item), 1);
                });
            }
        },
        created() {
            this.load_data();
        }
    };
</script>
