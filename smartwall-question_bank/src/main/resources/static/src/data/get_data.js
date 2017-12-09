import {
    fetch
} from './post_fetch';

export default {
    loadAllCategory: function() {
        return fetch('/v1/list/all_catagory');
    }
};