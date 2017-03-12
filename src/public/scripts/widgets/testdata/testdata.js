Backbone.widget({

    template: false,
    events: {

    },

    listen: {

    },

    loaded: function () {

        this.ajaxRequest({
            url: 'rest/tests?testId=1',
            data: {},
            type: "GET",
            success: function (response) {
                this.model = response[0];
                this.render();
            }
        });
    },

    render: function(){

        this.renderTemplate({

            template: 'questions',
            data: this.model,
            renderCallback: function () {

            }
        })

    }



}, []);