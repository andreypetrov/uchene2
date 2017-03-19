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

                _.each(response[0].testSections, function(section){
                    _.each(section.questions, function(question){
                        if(question.imageUrl){
                            question.imageUrl = 'assets/img/signs/' + question.imageUrl;
                        }
                        _.each(question.answers, function(answer){
                            if(question.correctAnswerId == answer.id){
                                answer.answerClass = 'correct-answer'
                            }else{
                                answer.answerClass = 'answer'
                            }
                        })
                    })
                })

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