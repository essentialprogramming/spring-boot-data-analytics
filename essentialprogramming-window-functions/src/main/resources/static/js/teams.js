docReady(async function () {

    let teams = transformJson(await getTeams());
    let table = document.querySelector('smart-table');

    table.dataSource = teams;
});


window.Smart('#table', class {
    get properties() {
        return {
            dataSource: new window.Smart.DataAdapter({
                async : true,
                dataFields: [
                    'team_group: string',
                    'team: string',
                    'points: number',
                    'rank: number'
                ]
            }),
            editing: false,
            selection: false,
            freezeHeader: true,
            paging: true,
            pageSize: 10,
            keyboardNavigation: false,
            sortMode: 'one',
            filtering: true,
            filterRow: true,
            tooltip: true,

            columns: [
                { label: 'Group', dataField: 'team_group', dataType: 'string'},
                { label: 'Team', dataField: 'team', dataType: 'string' },
                { label: 'Points', dataField: 'points', dataType: 'number', templateElement: '<span>{{value}}</span>' },
                {
                    label: 'Rank', dataField: 'rank', dataType: 'number', formatFunction(settings) {
                        if (settings.value === 1) {
                            settings.cell.style.backgroundColor = '#B6D7A8';
                        }
                        else {
                            settings.cell.style.backgroundColor = '';
                        }
                    }
                },
            ]
        };
    }
});






function transformJson(teams) {
    const json = teams.map((team) => (
        {
            team_group: team.group,
            team: team.team,
            points: team.points,
            rank: team.ranking
        }
    ));
    console.log(JSON.stringify(json, null, 2));
    return json;
}

async function getTeams() {

    let url = `/v1/all-teams-ranked`;
    let response = await fetch(url)
        .then(res => res.json())
        .then(json => {
            return json;

        })
        .catch(function (error) {
            console.log(error);
        });

    console.log(response)
    return response;
}