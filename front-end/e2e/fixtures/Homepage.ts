import { SrvRecord } from 'dns'
import {Selector} from 'testcafe'

fixture `Homepage`
.page('http://localhost:3000')

const idSelector = async (id : string) => {
    return await Selector(`[data-testid='${id}']`)
}

test('Test', async (t) => {
    await t.expect((await idSelector('menu-item-content')).innerText).eql(' Top Posts')
})